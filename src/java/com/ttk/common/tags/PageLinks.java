/**
 * @ (#) PageLinks.java Aug 8, 2005
 * Project      :
 * File         : PageLinks.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Aug 8, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import com.ttk.action.table.TableData;

/**
 *  This class looks for the TableData object in session and processes it
 *  to generate the HTML Page Links
 */
public class PageLinks extends TagSupport {

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( PageLinks.class );
    String strName = "";
    String strScope = "";
    
    public void setName(String strName) {
        this.strName = strName;
    }//end of setName(String strName)
    
    public void setScope(String strScope) {
        this.strScope = strScope;
    }//end of setScope(String strScope)
    
    public int doStartTag() throws JspException {
		try
		{
			TableData tableData = null;
            if(strScope.equals("request"))
            {
                tableData = (TableData)pageContext.getRequest().getAttribute(this.strName);
            }//end of if(strScope.equals("request"))
            else
            {
                tableData = (TableData)pageContext.getSession().getAttribute(this.strName);
            }//end of else
			
			//pageContext.getOut().print("<TR>");
			//pageContext.getOut().print("<TD CLASS=\"generalcontent\" colspan=2>");
			//pageContext.getOut().print("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			pageContext.getOut().print("<TR>");
			pageContext.getOut().print("<TD CLASS=\"generalcontent\">");
			ArrayList alTableInfo = tableData.getData();

			if ( alTableInfo != null && alTableInfo.size() > 0)
			{
				pageContext.getOut().print("Page&nbsp;&nbsp;");
			}//end of if ( alTableInfo.size() > 0)
			if(tableData.getPagePreviousLink())
			{
				pageContext.getOut().print("&nbsp;<a href=\"#\" onClick=\"javascript:PressBackWard()\"><img src=\"/ttk/images/Prev.gif\" alt=\"Previous Page\" width=\"4\" height=\"8\" border=\"0\"></a>&nbsp;&nbsp;");
			}//end of if(tableData.getPagePreviousLink())
			else
			{
				pageContext.getOut().print("&nbsp;");
			}//end of else
			int  iLoopNum = tableData.getCurrentNextRowCount() / tableData.getNoOfRowToFetch();
			int  iAddPageNum = (tableData.getPageLinkCount() * iLoopNum) - tableData.getPageLinkCount();
			int iToAdd = ((iLoopNum - 1) * tableData.getNoOfRowToFetch());
			//Page link part starts here
			int[] iPageLinks = tableData.getLinks();
			int iCurrentPage = tableData.getCurrentPage();
			if(iPageLinks == null)
			{
				iPageLinks = new int[0];
			}//end of if(iPageLinks == null)
			for(int i=0;i<iPageLinks.length;i++)
			{
				if(iPageLinks[i]!=iCurrentPage)
				{
					pageContext.getOut().print("<a href=\"#\" onClick=\"javascript:pageIndex("+iPageLinks[i]+")\" title=\"Page "+(iAddPageNum + iPageLinks[i])+"\">"+ (iAddPageNum + iPageLinks[i]) +"</a>");
					pageContext.getOut().print("&nbsp;");
				}//end of if(iPageLinks[i]!=iCurrentPage)
				else
				{
					pageContext.getOut().print(iAddPageNum + iPageLinks[i]);
					pageContext.getOut().print("&nbsp;");
				}//end of else
			}//end of for(int i=0;i<iPageLinks.length;i++)
			//Page link part ends here

			//To build the forward tab
			if(tableData.isPageNextLink())
			{
				pageContext.getOut().print("&nbsp;&nbsp;<a href=\"#\" onClick=\"javascript:PressForward()\"><img src=\"/ttk/images/Next.gif\" alt=\"Next Page\" width=\"4\" height=\"8\" border=\"0\"></a>");
			}//end of if(tableData.isPageNextLink())
			else
			{
				pageContext.getOut().print("&nbsp;");
			}//end of else
			pageContext.getOut().print("</td>");
			pageContext.getOut().print("<td class=\"generalcontent\" align=\"right\" id=\"right\" width=\"73%\">");
			int iTotalRecords = 0;
            if(tableData.getData() != null)
            {
                iTotalRecords = tableData.getData().size();
            }

            int iNumRowsDisp = tableData.getRowCount();
			int iStart = 1;
			int iEnd = 10;
			if(iTotalRecords < iNumRowsDisp)
			{
				iStart = 1;
				iEnd = iTotalRecords;
			}//end of if(iTotalRecords < iNumRowsDisp)
			else
			{
				if((iTotalRecords % iNumRowsDisp) == 0)
				{
					iStart = (iCurrentPage-1) * iNumRowsDisp+1;
					iEnd = iStart+iNumRowsDisp-1;
				}//end of if((iTotalRecords % iNumRowsDisp) == 0)
				else
				{
					if(iCurrentPage == iPageLinks.length)
					{
						iStart = (iCurrentPage-1) * iNumRowsDisp+1;
						iEnd = iTotalRecords;
					}//if(iCurrentPage == iPageLinks.length)
					else
					{
						iStart = (iCurrentPage-1) * iNumRowsDisp+1;
						iEnd = iStart+iNumRowsDisp-1;
					}//end of else
				}//end of else
			}//end of else
			if(iTotalRecords != 0)
			{
				pageContext.getOut().print("Results&nbsp;:&nbsp;"+ (iToAdd + iStart) +"&nbsp;-&nbsp;"+ (iToAdd + iEnd));
			}//end of if(iTotalRecords != 0)
			pageContext.getOut().print("</td>");
			pageContext.getOut().print("</tr>");
			//pageContext.getOut().print("</table>");
			//pageContext.getOut().print("</TD>");
			//pageContext.getOut().print("</TR>");
			//pageContext.getOut().print("</table>");

		}//end of try block
		catch (IOException ioe) {
			throw new JspException("Error: IOException in PageLinks !!!!!" + ioe.getMessage());
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			log.debug("error occured in PageLinks !!!!! ");
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag()


	public int doEndTag() throws JspException {
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
}//end of PageLinks