/**
 * @ (#) TreeComponent.java 12th Jan 2006
 * Project      :
 * File         : TreeComponent.java
 * Author       : Krishna K H
 * Company      :
 * Date Created : 12th Jan 2006
 *
 * @author       : Krishna K H
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
import com.ttk.action.tree.Icon;
import com.ttk.action.tree.Node;
import com.ttk.action.tree.TreeData;

/**
 *  This class looks for the TreeData object in session and processes it
 *  to generate the HTML Grid
 */
public class TreeComponent  extends TagSupport {

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( TreeComponent.class );
	String strName = "";
	String policy_status = null, strActiveSubLink= null;
	public void setName(String strName) {
        this.strName = strName;
     }

	public int doStartTag() throws JspException
    {
	  try {
		  
			TreeData treeData = (TreeData)pageContext.getSession().getAttribute(this.strName);
			policy_status =(String)pageContext.getSession().getAttribute("policy_status");
			strActiveSubLink = (String)pageContext.getSession().getAttribute("Corporate Policy");//strActiveSubLink);

		    log.debug(" -------- Inside TreeComponent ---------  ");
            if(treeData==null) // if Tree data is null in session then don't generate
            {
            	return SKIP_BODY;
            }//end of if(treeData==null)

			ArrayList alTitle = treeData.getTitle()==null?(new ArrayList()):treeData.getTitle();
	        ArrayList alObject = treeData.getRootData()==null?(new ArrayList()):treeData.getRootData();
            ArrayList rootSetting = treeData.getRootSettings();
            ArrayList nodeSetting = treeData.getNodeSettings();
            String strClassName = "";
            pageContext.getOut().println("<table class=\"rcBorder\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            try
			{
                int iRowCount = treeData.objTree.getRowCount();
				int iSelectedRoot=treeData.getSelectedRoot();
                //checking for row count,if row count is -1 it will display all the rows
			    if(treeData.objTree.getRowCount()==-1)
                {
				    iRowCount = alObject.size();
                }//end of if(treeData.obj1.getRowCount()==-1)
                int iStartIndex = (treeData.objTree.getCurrentPage()-1) * iRowCount + 1;
				if(iStartIndex > alObject.size())
				{
			        iStartIndex = (treeData.objTree.getCurrentPage()-2) * treeData.objTree.getRowCount() + 1;
					treeData.objTree.setCurrentPage((treeData.objTree.getCurrentPage()-1) <= 0 ? 1 : treeData.objTree.getCurrentPage()-1);
				}//end of if(iStartIndex > alObject.size())
				
			    if(iStartIndex < 0)
                {
					iStartIndex = 1;
                }//end of if(iStartIndex < 0)
				
				int iEndIndex = treeData.objTree.getCurrentPage() * iRowCount > alObject.size() ? alObject.size() : treeData.objTree.getCurrentPage() * iRowCount;
                if(alObject.size()>0)
				{
                    pageContext.getOut().println("<tr>");
					//loop through the data arraylist and display the data
					for(int iIndexCount=iStartIndex-1;iIndexCount<iEndIndex;iIndexCount++)
					{
	                       //Get the node properties to display information
							Node nodeProp = treeData.getTreeSetting().getRootNodeSetting();//(Node)alTitle.get(iNodeCount);
							strClassName = "specialGridHeader";  
                            if(!nodeProp.getColumnWidth().equals(""))
                            {
                                pageContext.getOut().println("<td CLASS="+strClassName+" width='"+nodeProp.getColumnWidth()+"'>");
                            }//end of if(!nodeProp.getColumnWidth().equals(""))
                            else
                            {
                                pageContext.getOut().println("<td CLASS="+strClassName+" >");
                            }//end of else
                        	// generate root id and imageid, required for javascript
                            
                        	String strIdDO="idDO"+String.valueOf(iIndexCount);
                       	    String strimgDO="imgDO"+String.valueOf(iIndexCount);
                        	
                        	// [+]  image on the root
                        	if(!treeData.isOnDemand())
                        	{
                        		pageContext.getOut().println("<img src=\"/ttk/images/c.gif\" width=\"16\" height=\"16\" onClick=\"javascript:showhide('"+strIdDO+"','"+strimgDO+"',2);\" name=\""+strimgDO+"\" border=\"0\" align=\"absmiddle\" alt=\"Expand\">");
                        		
                        	}//end of if(!treeData.isOnDemand())
                        	else if(iIndexCount==iSelectedRoot)    // [-] image, if root is the selected one the no need to resubmit the form, write a javascript function to expand
                        	{
                        		pageContext.getOut().println("<img src=\"/ttk/images/e.gif\" width=\"16\" height=\"16\" onClick=\"javascript:showhide('"+strIdDO+"','"+strimgDO+"',2);\" name=\""+strimgDO+"\" border=\"0\" align=\"absmiddle\" alt=\"Collapse\">");
                        	}//end of else if(iIndexCount==iSelectedRoot)
                        	else
                        	{
                        		pageContext.getOut().println("<img src=\"/ttk/images/c.gif\" width=\"16\" height=\"16\" onClick=\"javascript:OnShowhideClick('"+iIndexCount+"');\" name=\""+strimgDO+"\" border=\"0\" align=\"absmiddle\" alt=\"Expand\">");
                        	}//end of else
                        	//checking if data contains link or not
                            if(nodeProp.isLink())
                            {
                                String strUrl = "javascript:editRoot("+iIndexCount+")";
                                String strUrlValue = treeData.setObjectProperty(alObject.get(iIndexCount),nodeProp.getMethodName());
                                String strTitle = nodeProp.getLinkTitle();
                              
                                pageContext.getOut().print("&nbsp;<A href="+strUrl+" TITLE='"+strTitle+"' class=\""+strClassName+"\" >"+strUrlValue+"</A>");
                                
                            }//end of if(nodeProp.isRootLink())
                            else
                            {
                            	pageContext.getOut().println(" &nbsp;"+treeData.setObjectProperty(alObject.get(iIndexCount),nodeProp.getMethodName()));
                            }//end of else
                           
                            // Get the image list and Image tiltle for the root
                            ArrayList alRootIcons=null;
                            if(rootSetting.size()>iIndexCount)
                            {
                                alRootIcons=((Node)rootSetting.get(iIndexCount)).getIcons();
                            }//end of if(rootSetting.size()>iIndexCount)
                            else
                            {
                                alRootIcons=((Node)rootSetting.get(0)).getIcons();
                            }//end of else
                            if(alRootIcons!=null && alRootIcons.size()>0)
                            {
                            	int iTotalIconDisplayed=0;
                            	pageContext.getOut().println("<td width=\"45%\" align=\"right\" nowrap class=\"specialGridRow rcIcons\">");
                            	for(int iCount=0;iCount<alRootIcons.size();iCount++)
                            	{
                            		if(((Icon)alRootIcons.get(iCount)).isVisible())
                            		{
                            			if(iCount==0 || iCount==alRootIcons.size())//for first and last icon dont display icon separator
                            			{
                            				pageContext.getOut().print("");
                            			}//end of if(iCount==0 || iCount==alRootIcons.size())
                            			else if(iTotalIconDisplayed>0 && iCount==alRootIcons.size()) // display the icon if previous icon is displayed
                            			{
                            				pageContext.getOut().print("&nbsp;&nbsp;<img src=\"/ttk/images/IconSeparator.gif\"  width=\"1\" height=\"15\" align=\"absmiddle\" class=\"icons\">");
                            			}//end of else if(iTotalIconDisplayed>0 && iCount==alRootIcons.size())
                            			else if(iTotalIconDisplayed>0)
                            			{
                            				pageContext.getOut().print("&nbsp;&nbsp;<img src=\"/ttk/images/IconSeparator.gif\"  width=\"1\" height=\"15\" align=\"absmiddle\" class=\"icons\">");
                            			}//end of else if(iTotalIconDisplayed>0)

                                        if(pageContext.getSession().getAttribute("Corporate Policy")!=null)
                            			{
                                            if( strActiveSubLink.trim().equals("Corporate Policy") && policy_status.trim().equals("FTS")   && ((Icon)alRootIcons.get(iCount)).getImageTitle().trim().equals("Renew Members")  && ((Icon)alRootIcons.get(iCount)).getImageURL().trim().equals("DependentsIcon")  && iCount==0 )
                            				{
                            					pageContext.getSession().setAttribute("Corporate Policy","Corporate Policy");
                            					
                            					pageContext.getSession().removeAttribute("policy_status");
                            				}
                            				else
                            				{
                            					pageContext.getOut().print("&nbsp;&nbsp;<a href=\"#\" onClick=\"javascript:onRoot"+((Icon)alRootIcons.get(iCount)).getImageURL()+"('"+iIndexCount+"');\"><img src=\"/ttk/images/"+((Icon)alRootIcons.get(iCount)).getImageURL()+".gif\" TITLE=\""+((Icon)alRootIcons.get(iCount)).getImageTitle()+"\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a>");
                            				}

                            			} else 
                            			{
                            				pageContext.getOut().print("&nbsp;&nbsp;<a href=\"#\" onClick=\"javascript:onRoot"+((Icon)alRootIcons.get(iCount)).getImageURL()+"('"+iIndexCount+"');\"><img src=\"/ttk/images/"+((Icon)alRootIcons.get(iCount)).getImageURL()+".gif\" TITLE=\""+((Icon)alRootIcons.get(iCount)).getImageTitle()+"\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a>");
                            			}

                            			iTotalIconDisplayed++;
                            		}// end of if(((Icon)alRootIcons.get(iCount)).isVisible())
                            	}//end of for(int iCount=0;iCount<alRootIcons.size();iCount++)
                            	if(iTotalIconDisplayed==0){

                            		pageContext.getOut().println("&nbsp;");
                            	}
                            	
                            	pageContext.getOut().println("</td>");
                            	
                            }// end of if(alRootIcons!=null && alRootIcons.size()>0)
                            else{
                            	pageContext.getOut().println("<td width=\"45%\" align=\"right\" nowrap class=\"specialGridRow rcIcons\">");
                            	pageContext.getOut().println("&nbsp;</td>");
                                }

                            //////
                            //////   Call the child node function to get the ArrayList of nodes
                            //////
                            ArrayList nodeList=null;
                            Node childNodeProp = treeData.getTreeSetting().getChildNodeSetting();
                            if(!treeData.isOnDemand()) // if ondemand false then call node method for all the root else call for only selected root
                            {
                            	nodeList = treeData.callNodeFunction(alObject.get(iIndexCount),nodeProp.getNodeMethodName());//treeData.getNodeData();
                            }// end of if(!treeData.isOnDemand())
                            else if (iIndexCount==iSelectedRoot)
                            {
                                nodeList = treeData.callNodeFunction(alObject.get(iIndexCount),nodeProp.getNodeMethodName());//treeData.getNodeData();
                            }// end of if(iIndexCount==iSelectedRoot)

                            if(nodeList!=null && nodeList.size()>0)
                            {
                                int iTotalIconDisplayed=0;
                                if(!treeData.isOnDemand())
                                {
                                    pageContext.getOut().println("<tr id=\""+strIdDO+"\" style=\"display:none;\">");  // if ondemand close all the root node
                                }//end of if(!treeData.isOnDemand())
                            	else if((iIndexCount==iSelectedRoot))
                                {
                                    pageContext.getOut().println("<tr id=\""+strIdDO+"\" style=\"display:\"\";\">");   // else expand the root node for selected root
                                }//end of else if((iIndexCount==iSelectedRoot))
                            	else
                                {
                                    pageContext.getOut().println("<tr id=\""+strIdDO+"\" style=\"display:none;\">");
                                }//end of else
                            	pageContext.getOut().println("<td colspan=\"2\" class=\"specialGridRow\">");
                            	pageContext.getOut().println("<table class=\"rcLastLevel\"  border=\"0\" cellspacing=\"0\" cellpadding=\"1\">");

                            	for(int jCount=0;jCount<nodeList.size();jCount++)
                            	{
                                    ArrayList alNodeIcons=null;
                                    if(nodeSetting!=null && nodeSetting.size()>jCount)
                                    {
                                        alNodeIcons=((Node)nodeSetting.get(jCount)).getIcons();
                                        childNodeProp=((Node)nodeSetting.get(jCount));
                                    }
                                    else if(nodeSetting!=null && nodeSetting.size()>0)
                                    {
                                        alNodeIcons=((Node)nodeSetting.get(0)).getIcons();
                                        childNodeProp=((Node)nodeSetting.get(0));
                                    }
                             		pageContext.getOut().println("<tr>");
                            		pageContext.getOut().println("<td width=\"100%\" align=\"left\" class=\"rcLastLevelText\">");
                            		if(childNodeProp.isLink())
                                     {
 		                                String strUrl = "javascript:editNode('"+iIndexCount+"','"+jCount+"')";
 		                                String strUrlValue = treeData.setObjectProperty(nodeList.get(jCount),childNodeProp.getMethodName());
 		                                String strTitle = childNodeProp.getLinkTitle();
 		                                pageContext.getOut().println("&nbsp;<A href="+strUrl+" TITLE='"+strTitle+"' class=\"rcLastLevelText\" ><font color=\""+childNodeProp.getTextColor()+"\">"+strUrlValue+"</A></font>");
                                     }//end of if(nodeProp.isNodeLink())
                            		 else
                            		 {
                                        pageContext.getOut().print("<font color=\""+childNodeProp.getTextColor()+"\">");
                            		 	pageContext.getOut().print(""+treeData.setObjectProperty(nodeList.get(jCount),childNodeProp.getMethodName()));
                                        pageContext.getOut().print("</font>");
                            		 }//end of else
                            		pageContext.getOut().println("</td>");
                                    // Display the child image list.
                                    iTotalIconDisplayed=0;
                                    if(alNodeIcons.size()>0){
	                                    pageContext.getOut().println("<td align=\"right\" class=\"rcLastLevelIcons\" nowrap>");
	                                	for(int kCount=0;kCount<alNodeIcons.size();kCount++)
	                                	{
	                                        if(((Icon)alNodeIcons.get(kCount)).isVisible())
	                                        {
	                                            if(kCount==0 || kCount==alNodeIcons.size())//for first and last icon dont display icon separator
	                                            {
	                                                pageContext.getOut().print("");
	                                            }//end of if(kCount==0 || kCount==alNodeIcons.size())
	                                            else if(iTotalIconDisplayed>0 && kCount==alNodeIcons.size()) // display the icon if previous icon is displayed
	                                            {
	                                                pageContext.getOut().print("&nbsp;&nbsp;<img src=\"/ttk/images/IconSeparator.gif\"  width=\"1\" height=\"15\" align=\"absmiddle\" class=\"icons\">");
	                                            }//end of else if(iTotalIconDisplayed>0 && kCount==alNodeIcons.size())
	                                            else if(iTotalIconDisplayed>0)
	                                            {
	                                                pageContext.getOut().print("&nbsp;&nbsp;<img src=\"/ttk/images/IconSeparator.gif\"  width=\"1\" height=\"15\" align=\"absmiddle\" class=\"icons\">");
	                                            }//end of else if(iTotalIconDisplayed>0)
	                                            pageContext.getOut().print("&nbsp;&nbsp;<a href=\"#\" onClick=\"javascript:onNode"+((Icon)alNodeIcons.get(kCount)).getImageURL()+"('"+iIndexCount+"','"+jCount+"');\"><img src=\"/ttk/images/"+((Icon)alNodeIcons.get(kCount)).getImageURL()+".gif\" TITLE=\""+((Icon)alNodeIcons.get(kCount)).getImageTitle()+"\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\" class=\"icons\"></a>");
	                                            iTotalIconDisplayed++;
	                                        }// end of if(!alDisabledNode.contains(String.valueOf(kCount)))
	                                    }// end of for(int kCount=0;kCount<alNodeIcons.size();kCount++
	                                	
	                                	if(iTotalIconDisplayed==0){
	                                		pageContext.getOut().println("&nbsp;");
	                                	}
	                                	pageContext.getOut().println("</tr>");
                            	}// end of for(int jCount=0;jCount<nodeList.size();jCount++)
                                  
                            	}
                        	    pageContext.getOut().println("</table>");
                                pageContext.getOut().println("<br></td>");
                                pageContext.getOut().println("</tr>");
                            }// end of if(nodeList!=null && nodeList.size()>0 )
                            else // need change when ondemand flage is on
                            {
                            	if((iIndexCount==iSelectedRoot))
                            	{
                            		pageContext.getOut().println("<tr id=\""+strIdDO+"\" style=\"display:\"\";\">");
                            	}//end of if((iIndexCount==iSelectedRoot))
                            	else
                            	{
                            		pageContext.getOut().println("<tr id=\""+strIdDO+"\" style=\"display:none;\">");
                            	}//end of else
                            	pageContext.getOut().println("<td colspan=\"2\" class=\"specialGridRow\">");
                            	pageContext.getOut().println("<table class=\"rcLastLevel\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                            	pageContext.getOut().println("<td width=\"100%\" align=\"left\" class=\"rcLastLevelText\">");
                            	pageContext.getOut().println("<tr><td class=\"rcLastLevelText\" colspan=\"2\">&nbsp;&nbsp; No Data Found </td></tr>");
                            	pageContext.getOut().println("</td>");
                            	pageContext.getOut().println("</table>");
                                pageContext.getOut().println("</td>");
                                pageContext.getOut().println("</tr>");
                                
                            }// end of else
                            pageContext.getOut().println("</td>");
							//to check whethere iNodeCount is equal to data array list lost value for apending  end tr tag
							if(iIndexCount == alTitle.size()-1)
							{ 
								pageContext.getOut().println("</tr>");
							}//end of if(iNodeCount == alTitle.size()-1)
						//}//end of for(int iNodeCount=0;iNodeCount<alTitle.size();iNodeCount++)
					}// end of for(int iIndexCount=iStartIndex-1;iIndexCount<iEndIndex;iIndexCount++)
					
				}//end of if(alObject.size()>0)
				else
				{
					pageContext.getOut().println("<tr><td class=\"generalcontent\" colspan="+alTitle.size()+"><br>&nbsp;&nbsp; No Data Found <br>&nbsp;</td></tr>");
				}//end of else part
				pageContext.getOut().println("</table>");
				pageContext.getSession().removeAttribute("Corporate Policy");
			}//end of try block
			catch(Exception exp)
			{
				exp.printStackTrace();
				log.debug("error occured in TreeComponet !!!!! ");
			}//end of catch block
		}//end of try block
		catch (IOException ioe)
        { 
			throw new JspException("Error: IOException in TreeComponet !!!!!" + ioe.getMessage());
		}//end of catch (IOException ioe)
		return SKIP_BODY;
	}//end of doStartTag()

	public int doEndTag() throws JspException {
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
}//end of TreeComponent