/**
 * @ (#) ShortFallQueries.java Jul 06, 2006
 * Project      : TTK HealthCare Services
 * File         : ShortFallQueries.java
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : Jul 06, 2006
 *
 * @author      : Krishna K H
 * Modified by  :
 * Modified date:
 * Reason       :
*/

package com.ttk.common.tags.preauth;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;

/**
 *  This class builds the Policy History in the Pre-Auth History
 */

public class ShortFallQueries extends TagSupport{

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	private String flow;
    private static Logger log = Logger.getLogger( ShortFallQueries.class );

    public int doStartTag() throws JspException{
		Document document=null;
		StringBuilder queryIds=new StringBuilder();
		String raisedSelectBoxDis="";
		String raisedSelectBoxChecked="";
		String recievedSelectBoxDis="";
		String recievedSelectBoxChecked="";
		
		try
		{
			if("PAT".equals(getFlow()))	 document = (Document)pageContext.getSession().getAttribute("preauthShortfallQueries");
			else if("CLM".equals(getFlow()))document = (Document)pageContext.getSession().getAttribute("claimShortfallsQueries");
	        JspWriter out = pageContext.getOut();//Writer object to write the file
	        String queriesStatus=(String)pageContext.getSession().getAttribute("queriesStatus");
	        if("add".equals(queriesStatus)){
	        	raisedSelectBoxDis="";
	        	raisedSelectBoxChecked="";
	        	recievedSelectBoxDis="disabled";
	    		recievedSelectBoxChecked="";
	        }else  if("new".equals(queriesStatus)){
	        	raisedSelectBoxDis="disabled";
	        	raisedSelectBoxChecked="checked";
	        	recievedSelectBoxDis="";
	    		recievedSelectBoxChecked="";
	        }
	       
	        if(document != null){
	        List<Node>	subsectionNodeList=document.selectNodes("shortfall/section[@name!='Others']/subsection");
	       
	        int ii=1;
	       
	        for(Node subsectionNode:subsectionNodeList){
	        	List<Node> queryNodes=document.selectNodes("shortfall/section/subsection[@name='"+subsectionNode.valueOf("@name")+"']/query");
	        if(queryNodes!=null&&queryNodes.size()>=1){
	        	
	        	 String pedName="'ped"+ii+"'";
	         	String QuerieName="'Querie"+ii+"'";
	         	String functionName="\"showhide("+QuerieName+","+pedName+")\"";
	        	out.print("<fieldset>");
	        	out.print("<legend>");
	        	out.print("<img src='/ttk/images/c.gif' alt='Expand' name="+pedName+" width='16' height='16' align='top' onClick="+functionName+">"+subsectionNode.valueOf("@displayName"));
	        	out.print("</legend>");
	        	out.print("<table align='center' class='formContainerWithoutPad' border='0' cellspacing='0' cellpadding='0'  id="+QuerieName+" style='display:none;'>");
	        	out.print("<tr>");
	        	out.print("<td width='88%' class='formLabel'>");
	        	out.print("</td>");
	        	out.print("<td width='18%' nowrap align='right' class='formLabel'>Recieved</td>");
	        	out.print("</tr>");
	        	String name=subsectionNode.valueOf("@name");
	        	
	        	 List<Node>	queryNodeList=document.selectNodes("shortfall/section/subsection[@name='"+name+"']/query");
	        	 for(Node queryNode:queryNodeList){
	        		 out.print("<tr>");
	        		 out.print("<td width='88%' class='formLabel'>");
	        		 String id=queryNode.valueOf("@id");
	        		 queryIds.append(id);queryIds.append("|");
	        		 String postlabel=queryNode.valueOf("@postlabel");
	        		 
	        		 if("old".equals(queriesStatus)){
	        			 if("YES".equals(queryNode.valueOf("@received"))){
	     	        	raisedSelectBoxDis="disabled";
	     	        	raisedSelectBoxChecked="checked";
	     	        	recievedSelectBoxDis="disabled";
	     	    		recievedSelectBoxChecked="checked";
	        			 }else{
	        				 raisedSelectBoxDis="disabled";
	 	     	        	raisedSelectBoxChecked="checked";
	 	     	        	recievedSelectBoxDis="";
	 	     	    		recievedSelectBoxChecked=""; 
	        			 }// }else{
	     	        }// if("old".equals(queriesStatus)){
	        		 out.print("<input type='checkbox' onclick=\"setValue(this);\" "+raisedSelectBoxDis+"   name='allQueries' value='"+id+"' id='"+id+"' "+raisedSelectBoxChecked+">"+postlabel);
	        		 out.print("</td>");
	        		 out.print("<td width='18%' nowrap align='right' class='formLabel'>");
	        		 out.print("<input type='checkbox' onclick='setProcess()'  name='QueriesRecieved' "+recievedSelectBoxDis+"  value='"+id+"'  id='"+id+"' "+recievedSelectBoxChecked+">");
	        		 out.print("</td>");
	        		 out.print("</tr>");
	        	 }// for(Node queryNode:queryNodeList){
	        	
	        	 out.print("</table>");
        		 out.print("</fieldset>");	
        		 ii++;
	        }//if(queryNodes!=null&&queryNodes.size()>=1){
        }//for(Node subsectionNode:subsectionNodeList){
	        
	        Node	otherQueryNode=document.selectSingleNode("shortfall/section[@name='Others']/subsection/query");
    		if(otherQueryNode!=null){
    			String value=otherQueryNode.valueOf("@value");
    			value=value==null?"":value;
    			if("add".equals(queriesStatus)||value.length()>2){
    			out.print("<fieldset>");
	        	out.print("<legend>");
	        	out.print("<img src='/ttk/images/c.gif' alt='Expand' onClick=\"showhide('Querie0','ped0')\" width='16' height='16' align='top' name='ped0'>OtherQueries");
	        	out.print("</legend>");
	        	out.print("<table align='center' class='formContainerWithoutPad' border='0' cellspacing='0' cellpadding='0'  id='Querie0' style='display:none;'>");
	        	out.print("<tr>");
	        	out.print("<td width='88%' class='formLabel'>");
	        	out.print("</td>");
	        	out.print("<td width='18%' nowrap align='right' class='formLabel'>Recieved</td>");
	        	out.print("</tr>");
    			out.print("<tr>");
        		out.print("<td  width='88%' class='formLabel'>");
        		String areaBoxDis="";
        		String areaCheckBoxDis="";
        		String areaCheckBoxChecked="";
        		if("add".equals(queriesStatus)){
        			areaCheckBoxDis="disabled";
        		}else if("YES".equals(otherQueryNode.valueOf("@received"))){
        			areaBoxDis="disabled";
        			areaCheckBoxChecked="checked";
        			areaCheckBoxDis="disabled";
        		}
        		
        		
        		out.print("<textarea name='OtherQueries' "+areaBoxDis+" class='textBox textAreaLarge10Lines'  id='OtherQueries'>"+value+"</textarea>"); 
        		out.print("</td>");
        		 out.print("<td width='18%' nowrap align='right' class='formLabel' style='vertical-align: top;'>");
        		 out.print("<input type='checkbox' onclick='setProcess()'  name='QueriesRecieved' "+areaCheckBoxDis+" value='OtherQuery.1'  id='OtherQuery.1' "+areaCheckBoxChecked+">");
        		 out.print("</td>");
        		out.print("</tr>");
        		 out.print("</table>");
        		 out.print("</fieldset>");
    			}
    	  }//if(queryNodes!=null&&queryNodes.size()>=1){
	        
	   }//end of if(document != null)
	   out.print("<input type='hidden' name='queryIds' value=\""+queryIds+"\">");	
		}//end of try
		catch(Exception exp)
        {
            exp.printStackTrace();
        }//end of catch block
        return SKIP_BODY;
	}//end of doStartTag()
    public int doEndTag() throws JspException {
        return EVAL_PAGE;//to process the rest of the page
    }//end doEndTag()
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
}//end of class PolicyHistory