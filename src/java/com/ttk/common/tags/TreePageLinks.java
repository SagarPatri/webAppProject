/**
 * @ (#) TreePageLinks.java Jan 16th, 2005
 * Project      :
 * File         : TreePageLinks.java
 * Author       : Krishna K H
 * Company      :
 * Date Created : Jan 16th, 2005
 *
 * @author       :  Krishna K H
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
import com.ttk.action.tree.TreeData;

/**
 *  This class looks for the TreeData object in session and processes it
 *  to generate the HTML Tree Page Links
 */
public class TreePageLinks extends TagSupport {

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( TreePageLinks.class );
    String strName = "";
    public void setName(String strName)
    {
        this.strName = strName;
    }//end of setName(String strName)
    public int doStartTag() throws JspException {
		try
		{
			TreeData treeData = (TreeData)pageContext.getSession().getAttribute(this.strName);
			// if no data is there in session or no element in the root data return
			if(treeData==null || treeData.getRootData()==null ||treeData.getRootData().size()==0)
			{
				return SKIP_BODY;
			}//end of if(treeData==null || treeData.getRootData()==null ||treeData.getRootData().size()==0)
			pageContext.getOut().print("<TR>");
			pageContext.getOut().print("<TD CLASS=\"generalcontent\">");
			ArrayList alTreeInfo = treeData.getRootData();
			if ( alTreeInfo.size() > 0)
			{
				pageContext.getOut().print("Page&nbsp;&nbsp;");
			}//end of if ( alTreeInfo.size() > 0)
			if(treeData.getPagePreviousLink())
			{
				pageContext.getOut().print("&nbsp;<a href=\"#\" onClick=\"javascript:PressBackWard()\"><img src=\"/ttk/images/Prev.gif\" alt=\"Previous Page\" width=\"4\" height=\"8\" border=\"0\"></a>&nbsp;&nbsp;");
			}//end of if(treeData.getPagePreviousLink())
			else
			{
				pageContext.getOut().print("&nbsp;");
			}//end of else

			int iLoopNum = treeData.getCurrentNextRowCount() / treeData.getNoOfRowToFetch();
			int iAddPageNum = (treeData.getPageLinkCount() * iLoopNum) - treeData.getPageLinkCount();
			int iToAdd = ((iLoopNum - 1) * treeData.getNoOfRowToFetch());

			//////////Page link part starts here

			int[] iPageLinks = treeData.getLinks();
			int iCurrentPage = treeData.getCurrentPage();
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

			//////////Page link part ends here

			//To build the forward tab
			if(treeData.isPageNextLink())
			{
				pageContext.getOut().print("&nbsp;&nbsp;<a href=\"#\" onClick=\"javascript:PressForward()\"><img src=\"/ttk/images/Next.gif\" alt=\"Next Page\" width=\"4\" height=\"8\" border=\"0\"></a>");
			}//end of if(treeData.isPageNextLink())
			else
			{
				pageContext.getOut().print("&nbsp;");
			}//end of else
			pageContext.getOut().print("</td>");
			pageContext.getOut().print("<td class=\"generalcontent\" align=\"right\" nowrap id=\"right\" width=\"73%\">");
			int iTotalRecords = treeData.getRootData().size();
			int iNumRowsDisp = treeData.getRowCount();
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
		}//end of try block
		catch (IOException ioe) {
			throw new JspException("Error: IOException in TreePageLinks !!!!!" + ioe.getMessage());
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
}//end of TreePageLinks