/**
 * @ (#) RoleTree.java Jan 10, 2006
 * Project      : TTK HealthCare Services
 * File         : RoleTree.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jan 10, 2006
 *
 * @author       :  Arun K N
 * Modified by   :  Arun K N
 * Modified date :  May 18, 2007
 * Reason        :  For displaying the link names from the displayname attribute.
 */

package com.ttk.common.tags;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import com.ttk.common.TTKCommon;

/**
 * This class is the customised tag library for displaying
 * Role details in the tree structure.
 * It reads the User Profile document and displays it in tree strucure.
 *
 */
public class RoleTree extends TagSupport {

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( RoleTree.class );
    /**
     * This method will be executed when customised tag begins
     * @return int
     * @throws JspException
     */
    public int doStartTag() throws JspException
    {
       try
       {
            Document userProfileDoc = (Document)pageContext.getRequest().getAttribute("UserProfileDoc");
            JspWriter out = pageContext.getOut();
            //boolean viewmode=true;
            String strViewMode="disabled";
            HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
            if(TTKCommon.isAuthorized(request,"Edit"))
            {
                strViewMode="";
            }//end of if(TTKCommon.isAuthorized(request,"Edit") || TTKCommon.isAuthorized(request,"Add"))

            if(userProfileDoc!=null)
            {
                //initialize the variables
                List linkList=null;
                List subLinkList=null;
                List tabList=null;
                List permissionList=null;

                Element eleLink=null;
                Element eleSubLink=null;
                Element eleTab=null;
                Element eleACL=null;

                String strLinkCnt="";
                String strSubLinkCnt="";
                String strTabCnt="";
                String strAclCnt="";

                String strLink="";
                String strSubLink="";
                String strTab="";

                //get the links from the document
                linkList=userProfileDoc.selectNodes("/SecurityProfile/Link");
                if(linkList!=null && linkList.size()>0)
                {
                    strLink="";
                    out.print("<table class=\"rcBorder\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                    for(int iLinkCnt=0;iLinkCnt<linkList.size();iLinkCnt++)
                    {
                        //get the current link and its details to display
                        eleLink=(Element)linkList.get(iLinkCnt);
                        strLink=eleLink.valueOf("@name");
                        strLinkCnt=iLinkCnt>=10? String.valueOf(iLinkCnt):"0"+iLinkCnt;
                        out.print("<tr>");
                        out.print("<td width=\"100%\" class=\"specialGridHeader\">");
                        out.print("<img src=\"/ttk/images/c.gif\" width=\"16\" height=\"16\" ");
                        out.print("onClick=\"showhide('"+strLink+iLinkCnt+"','img"+strLinkCnt+"',2);\" name=\"img"+strLinkCnt+"\" border=\"0\" ");
                        out.print("align=\"absmiddle\" alt=\"Expand\">&nbsp; "+ eleLink.valueOf("@displayname")+"</td>");
                        out.print("<td align=\"right\" nowrap class=\"specialGridRow rcIcons\">");
                        out.print("<input name=\"chkPermissions\" type=\"checkbox\" value=\"\" "+(eleLink.valueOf("@applicable").equals("Y")? "checked":"")+" id=\"chk"+strLinkCnt+"\" ");
                        out.print("onclick=\"javascript:checkAllById(this,document.forms[1])\" "+strViewMode+" ></td>");
                        out.print("</tr>");

                        //get all the sublinks for the current Link
                        subLinkList=userProfileDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink");

                        if(subLinkList!=null && subLinkList.size()>0)
                        {
                            strSubLink="";
                            out.print("<tr id=\""+strLink+iLinkCnt+"\" style=\"display:none;\">");
                            out.print("<td colspan=\"2\" class=\"specialGridRow\">");
                            out.print("<table class=\"rcLastLevel\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");

                            for(int iSubLinkCnt=0;iSubLinkCnt<subLinkList.size();iSubLinkCnt++)
                            {
                                //get the current sublink and its details to display
                                eleSubLink=(Element)subLinkList.get(iSubLinkCnt);
                                //strSubLink=eleSubLink.attribute("name").getText();
                                strSubLink=eleSubLink.valueOf("@name");
                                strSubLinkCnt=iSubLinkCnt>=10? String.valueOf(iSubLinkCnt):"0"+iSubLinkCnt;

                                out.print("<tr>");
                                out.print("<td align=\"left\" class=\"specialSubGridHeader\">");
                                out.print("<img src=\"/ttk/images/c.gif\" width=\"16\" height=\"16\" ");
                                out.print("onClick=\"showhide('"+strSubLink+iLinkCnt+iSubLinkCnt+"','img"+strLinkCnt+strSubLinkCnt+"',1);\" ");
                                out.print("name=\"img"+strLinkCnt+strSubLinkCnt+"\" border=\"0\" align=\"absmiddle\" alt=\"Expand\">"+eleSubLink.valueOf("@displayname")+"</td>");

                                out.print("<td width=\"3%\" align=\"right\" nowrap class=\"specialSubGridHeader\">");
                                out.print("<input name=\"chkPermissions\" type=\"checkbox\" value=\"\" "+(eleSubLink.valueOf("@applicable").equals("Y")? "checked":"")+" id=\"chk"+strLinkCnt+strSubLinkCnt+"\" ");
                                out.print("onclick=\"javascript:checkAllById(this,document.forms[1]);javascript:toCheckBoxById(this,document.forms[1])\" "+strViewMode+" ></td>");
                                out.print("</tr>");

                                //get all the tabs for the current SubLink
                                tabList=userProfileDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@name='"+strSubLink+"']/Tab");

                                if(tabList!=null && tabList.size()>0)
                                {
                                   strTab="";
                                   out.print("<tr id=\""+strSubLink+iLinkCnt+iSubLinkCnt+"\" style=\"display:none;\">");
                                   out.print("<td colspan=\"2\" class=\"specialGridRow\">");
                                   out.print("<table class=\"rcLastLevel\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");

                                   for(int iTabCnt=0;iTabCnt<tabList.size();iTabCnt++)
                                   {
                                      //select the current tab and its details to display
                                      eleTab=(Element)tabList.get(iTabCnt);
                                      //strTab=eleTab.attribute("name").getText();
                                      strTab=eleTab.valueOf("@name");
                                      strTabCnt=iTabCnt>=10? String.valueOf(iTabCnt):"0"+iTabCnt;

                                      out.print("<tr>");
                                      out.print("<td align=\"left\" class=\"specialSubGridHeader\">&bull;&nbsp;&nbsp;"+eleTab.valueOf("@displayname")+"</td>");
                                      out.print("<td width=\"3%\" align=\"right\" nowrap class=\"specialSubGridHeader\">");
                                      out.print("<input name=\"chkPermissions\" type=\"checkbox\" value=\"\" "+(eleTab.valueOf("@applicable").equals("Y")? "checked":"")+" id=\"chk"+strLinkCnt+strSubLinkCnt+strTabCnt+"\" ");
                                      out.print("onclick=\"javascript:checkAllById(this,document.forms[1]);javascript:toCheckBoxById(this,document.forms[1])\" "+strViewMode+" ></td>");
                                      out.print("</tr>");

                                      //get the ACL permissions for the current tab
                                      permissionList=userProfileDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@name='"+strSubLink+"']/Tab[@name='"+strTab+"']/ACL/permission");

                                      if(permissionList!=null && permissionList.size()>0)
                                      {
                                          for(int iPermissionCnt=0;iPermissionCnt<permissionList.size();iPermissionCnt++)
                                          {
                                              //get the current permission and display name to display in tree
                                              eleACL=(Element)permissionList.get(iPermissionCnt);
                                              strAclCnt=iPermissionCnt>=10? String.valueOf(iPermissionCnt):"0"+iPermissionCnt;

                                              out.print("<tr>");
                                              out.print("<td align=\"left\" class=\"rcLastLevelText\">&nbsp;&nbsp;&nbsp;&nbsp;"+eleACL.valueOf("@displayname")+"</td>");
                                              out.print("<td width=\"3%\" align=\"right\" nowrap class=\"specialSubGridHeader\">");
                                              out.print("<input name=\"chkPermissions\" type=\"checkbox\" value=\""+eleACL.valueOf("@name")+"\" ");
                                              out.print((eleACL.valueOf("@applicable").equals("Y")? "checked":"")+" id=\"chk"+strLinkCnt+strSubLinkCnt+strTabCnt+strAclCnt+"\" ");
                                              out.print("onclick=\"javascript:toCheckBoxById(this,document.forms[1])\" "+strViewMode+" ></td>");
                                              out.print("</tr>");
                                          }//end of for(int iPermissionCnt=0;iPermissionCnt<permissionList.size();iPermissionCnt++)
                                      }//end of if(permissionList!=null && permissionList.size()>0)
                                   }//end of for(int iTabCnt=0;iTabCnt<tabList.size();iTabCnt++)
                                   out.print("</table><br>");
                                   out.print("</td>");
                                   out.print("</tr>");
                                }//end of if(tabList!=null && tabList.size()>0)
                            }//end of for(int iSubLinkCnt=0;iSubLinkCnt<subLinkList.size();iSubLinkCnt++)
                            out.print("</table><br>");
                            out.print("</td>");
                            out.print("</tr>");
                        }//end of if(subLinkList!=null && subLinkList.size()>0)
                    }//end of for(int iLinkCnt=0;iLinkCnt<linkList.size();iLinkCnt++)
                    out.print("</table>");
                }//end of if(linkList!=null && linkList.size()>0)
            }//end of if(userProfileDoc!=null)
       }//end of try
       catch (IOException ioe)
       {
           throw new JspException("Error: IOException in RoleTree Tag Library!!!" + ioe.getMessage());
       }//end of catch (IOException ioe)
       catch(Exception exp)
       {
           log.debug("error occured in RoleTree Tag Library!!!");
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
}//end of RoleTree