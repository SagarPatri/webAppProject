 /**

  * @ (#) ClauseListTag.java June 29, 2006
  * Project : TTK HealthCare Services
  * File : ClauseListTag.java
  * Author : Unni V M
  * Company : Span Systems Corporation
  * Date Created : June 29, 2006
  *
  * @author : Unni V M
  * Modified by :
  * Modified date :
  * Reason :
  */

 package com.ttk.common.tags.preauth;

 import java.util.List;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.tagext.TagSupport;
 import org.apache.log4j.Logger;
 import org.dom4j.Document;
 import org.dom4j.Element;
// import org.dom4j.Node;

 /**
  *  This class builds the List of Selected Clauses  in the Pre-Auth RuleData
  */

 public class ClauseListTag extends TagSupport {
	 
	 /**
	  * Comment for <code>serialVersionUID</code>
	  */
	 private static final long serialVersionUID = 1L;
	 
	 private static Logger log = Logger.getLogger( ClauseListTag.class );
	 
	 public int doStartTag() throws JspException{
		 try
		 {
			 log.debug("Inside the ClauseListTag");
			 
			 String [] strClauses = (String [])pageContext.getRequest().getAttribute("Clauses");
			 Document ruleDocument = (Document)pageContext.getSession().getAttribute("BaseRuleDocument");
			 JspWriter out = pageContext.getOut();
			 
			 if(ruleDocument!=null)
			 {
				 Element eleClause=null;
				 List clauseList=null;
				 //get all the clauses from the Base Rule
				 clauseList=ruleDocument.selectNodes("//clause");
				 if(clauseList!=null && clauseList.size()>0)
				 {
					 out.println("<table align='center' class='formContainer' border='0' cellspacing='0' cellpadding='0'>");
					 out.println("<tr><td><a href=\"#\" class=\"fieldGroupHeader\" onClick=\"onCheckAll(true)\">Check All</a> | <a href=\"#\" class=\"fieldGroupHeader\" onClick=\"onCheckAll(false)\">Clear All</a></td></tr>");
					 
					 for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
					 {
						 eleClause=(Element)clauseList.get(iClauseCnt);
						 if(!eleClause.valueOf("@name").equals("Global"))
						 {
							 out.println("<tr><td class='formLabel'>");
							 out.println("<input type=\"checkbox\"   value=\""+eleClause.valueOf("@id")+"\" name=\"chkbox\" "+((isClauseSelected(eleClause.valueOf("@id"),strClauses)==true)? " checked ":"")+" >&nbsp;&nbsp;");
							 out.println(eleClause.valueOf("@name"));
							 out.println("</td></tr>");
						 }//end of if(!eleClause.valueOf("@name").equals("Global"))
					 }//end of for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
					 out.println("</table>");
				 }//end of if(clauseList!=null && clauseList.size()>0)
			 }//end of if(ruleDocument!=null)
		 }//end of try
		 catch(Exception e)
		 {
			 throw new JspException("Error: in ClauseList Tag Library!!!" );
		 }//end of catch(Exception e)
		 return SKIP_BODY;
	 }//end of doStartTag()
	 
	 /**
	  * This method checks whether Clause is already selected or not
	  * and returns true/false.
	  * @param strFieldName String Clause Id
	  * @param strClauseIds String[] Array of selected Clause Ids
	  * @return blnFlag boolean value
	  */
	 private boolean isClauseSelected(String strFieldName,String[] strClauseIds)
	 {
		 boolean blnFlag=false;
		 if(strClauseIds!=null)
		 {
			 for(int iClauseCnt=0;iClauseCnt<strClauseIds.length;iClauseCnt++)
			 {
				 if(strClauseIds[iClauseCnt].equals(strFieldName))
				 {
					 blnFlag=true;
					 break;
				 }//end of if(strClauseIds[iClauseCnt].equals(strFieldName))
			 }//end of for(int iClauseCnt=0;iClauseCnt<strClauseIds.length;iClauseCnt++)
		 }//end of if(strClauseIds!=null)
		 return blnFlag;
	 }//end of isClauseSelected(String strFieldName,String[] strClauseIds)
	 
	 public int doEndTag() throws JspException {
		 return EVAL_PAGE;//to process the rest of the page
	 }//end doEndTag()
	 
 }//end of ClauseListTag
