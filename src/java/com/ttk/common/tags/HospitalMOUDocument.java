/**
 * @ (#) HospitalMOUDocument.java Dec 8, 2005
 * Project      :
 * File         : HospitalMOUDocument.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Dec 8, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.common.TTKCommon;

/**
 *  This class looks for the MOU Document object in session and processes it
 *  to generate the HTML content
 */
public class HospitalMOUDocument  extends TagSupport{

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( HospitalMOUDocument.class );

    public int doStartTag() throws JspException{
        try {

            Document doc = (Document)pageContext.getRequest().getAttribute("MOUDocument");
            String strViewmode=" Disabled ";
            if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
			{
				strViewmode="";
			}//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
            if(doc != null)
            {
                ArrayList alArticles = null;
                if(!((List)doc.selectNodes("/MOU/Article")).isEmpty())
                {
                    alArticles = (ArrayList)doc.selectNodes("/MOU/Article");//MOUDocument.getArticleList();
                }//end of if
                ArrayList alClauses = null;
                Element articleElement = null;
                Element clauseElement = null;
                if(alArticles != null && alArticles.size() > 0)
                {
                    String strChecked = "";
                    String strDisabled = "";
                    String strClauseNbr = "";
                    int iCounter = 0;
                    int iCount = 0;
                    alClauses = null;
                    for(int j=0; j < alArticles.size(); j++)
                    {
                        articleElement = (Element)alArticles.get(j);
                        iCount = iCounter;
                        alClauses = (ArrayList)doc.selectNodes("/MOU/Article[@name='"+articleElement.attribute("name").getText()+"']/Clause");//MOUDocument.getClauseList(cacheObject.getCacheId());

                        pageContext.getOut().print("<div class=\"contentArea\" id=\"contentArea\">");
                        pageContext.getOut().print("<br>");
                        pageContext.getOut().print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                        pageContext.getOut().print("<tr>");
                        pageContext.getOut().print("<td class=\"formLabelBold\">"+articleElement.attribute("name").getText()+": " +articleElement.attribute("desc").getText()+"</td>");
                        pageContext.getOut().print("</tr>");
                        pageContext.getOut().print("</table>");
                        pageContext.getOut().print("<fieldset>");
                        pageContext.getOut().print("<legend>Clause Details</legend>");
                        pageContext.getOut().print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");

                        if(alClauses != null && alClauses.size() > 0 )
                        {
                            clauseElement = null;
                            for(int i=0; i < alClauses.size(); i++)
                            {
                                clauseElement = (Element)alClauses.get(i);
                                strClauseNbr = clauseElement.attribute("number").getText();
                                if((clauseElement.attribute("applicable").getText()).equals("Y"))
                                {
                                    strChecked = "checked";
                                    strDisabled = "";
                                }//end of if
                                else
                                {
                                    strChecked = "";
                                    strDisabled = "disabled='true'";
                                }//end of else

                                pageContext.getOut().print("<tr>");
                                pageContext.getOut().print("<td width=\"10%\" class=\"formLabel\">Clause No.:</td>");
                                pageContext.getOut().print("<td class=\"formLabelBold\">"+strClauseNbr+"</td>");
                                pageContext.getOut().print("<td class=\"formLabel\" align=\"right\" style=\"padding-right:10px; \"><input name=\"chkApplicable\" type=\"checkbox\"" + strChecked +" onclick=\"SetState(this, '"+(i + iCount)+"', '"+strClauseNbr+"')\" "+strViewmode+">");
                                pageContext.getOut().print("Applicable</td>");
                                pageContext.getOut().print("</tr>");
                                if((clauseElement.attribute("applicable").getText()).equals("Y"))
                                {
                                    pageContext.getOut().print("<INPUT TYPE=\"hidden\" NAME=\"nonapplicable\" VALUE=\"\">");
                                    pageContext.getOut().print("<INPUT TYPE=\"hidden\" NAME=\"applicable\" VALUE=\""+strClauseNbr+"\">");
                                }//end if
                                else
                                {
                                    pageContext.getOut().print("<INPUT TYPE=\"hidden\" NAME=\"nonapplicable\" VALUE=\""+strClauseNbr+"\">");
                                    pageContext.getOut().print("<INPUT TYPE=\"hidden\" NAME=\"applicable\" VALUE=\"\">");
                                }//end else
                                pageContext.getOut().print("<tr>");
                                pageContext.getOut().print("<td colspan=\"3\" valign=\"top\" class=\"formLabel\"><textarea name=\"Description\" class=\"textBox textAreaLarge\" "+ strDisabled +" style=\"width:99%;\" "+strViewmode+">"+clauseElement.getText()+"</textarea></td>");
                                pageContext.getOut().print("</tr>");
                                //increment the counter
                                iCounter++;

                                if(i != (alClauses.size() -1))
                                {
                                    pageContext.getOut().print("<tr>");
                                    pageContext.getOut().print("<td colspan=\"3\" valign=\"top\" class=\"formLabel\">&nbsp;</td>");
                                    pageContext.getOut().print("</tr>");
                                }//end of if(i != (alClauses.size() -1))

                            }//end of for
                        }//end of if
                        pageContext.getOut().print("</table>");
                        pageContext.getOut().print("</fieldset>");

                    }//end of for(int j=0; j < alArticles.size(); j++)
                    articleElement = null;
                    alClauses = null;
                    strChecked = "";
                    strDisabled = "";
                    strClauseNbr = "";
                }//end of if(alArticles != null && alArticles.size() > 0)
            }//end of if(doc != null)

        }//end of try block
        catch (IOException ioe) {
            throw new JspException("Error: IOException in HospitalMOUDocument Tag Library!!!!!" + ioe.getMessage());
        }//end of catch (IOException ioe)
        catch(Exception exp)
        {
            exp.printStackTrace();
            log.debug("error occured in HospitalMOUDocument Tag Library!!!!! ");
            //throw new TTKException(exp, "");
        }//end of catch block
        return SKIP_BODY;
    }//end of doStartTag()

    public int doEndTag() {
        return EVAL_PAGE;//to process the rest of the page
    }//end doEndTag()
}//end of class HospitalMOUDocument