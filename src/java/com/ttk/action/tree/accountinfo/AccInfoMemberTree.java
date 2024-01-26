/**
 * @ (#) AccInfoMemberTree.java 26th July 2007
 * Project      :
 * File         : AccInfoMemberTree.java
 * Author       : Raghavendra T M
 * Company      :
 * Date Created : 26th July 2007
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.tree.accountinfo;

import com.ttk.action.tree.Node;
import com.ttk.action.tree.Tree;

/**
 * This class provides the information of Member Tree
 */
public class AccInfoMemberTree extends Tree
{
	private String  strTreeType;
	public AccInfoMemberTree(String strTreeType){
    	 this.strTreeType = strTreeType;
    }// end of constructer
	
	/**
	 * This creates the columnproperties objects for each and
	 * every column and adds the column object to the table
	 */
    public void setTreeProperties() 
    {
    	String strRootImagelist[]={"ChangePassword"};
		String strNodeImagelist[]={};
		String strRootImageTitle[]={"Change Password"};
		String strNodeImageTitle[]={};
		
		setRowCount(10);
		setCurrentPage(1);
		setPageLinkCount(10);
		//Setting properties for Root
		Node nodeRoot= new Node();
		nodeRoot.setWidth(55);
		nodeRoot.setMethodName("getName");
        nodeRoot.setNodeMethodName("getMemberList");
        if("COR".equals(strTreeType)||"NCR".equals(strTreeType))
        nodeRoot.setIsLink(true);
       	nodeRoot.setIcons(createIcons(strRootImagelist,strRootImageTitle));
       	setRootSetting(nodeRoot);
        Node nodeChild = new Node();
        nodeChild.setWidth(55);
        nodeChild.setMethodName("getName");
        nodeChild.setIcons(createIcons(strNodeImagelist,strNodeImageTitle));
        nodeChild.setTextColor("blue");
        nodeChild.setIsLink(true);
        setChildNodeSetting(nodeChild);
  	}//end of public void setTreeProperties()
}//end of class AccInfoMemberTree

