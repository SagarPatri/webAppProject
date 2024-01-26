/**
 * @ (#) OnlineMemberTree.java 11th Jul 2006
 * Project      :
 * File         : OnlineMemberTree.java
 * Author       : Krishna K H
 * Company      :
 * Date Created : 11th Jul 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.tree.onlineforms;

import com.ttk.action.tree.Node;
import com.ttk.action.tree.Tree;


/**
 * This class provides the information of Member Tree
 */
public class OnlineMemberTree extends Tree
{
	/**
	 * This creates the columnproperties objects for each and
	 * every column and adds the column object to the table
	 */
	public void setTreeProperties()
	{
		//1352
		String strRootImagelist[]={"ChangePassword","eCardIcon","AddIcon","CancelledIcon","SendConfirmation","OPTOUT1","ViewFiles"};
		String strNodeImagelist[]={"CancelledIcon","BrowseIcon"};//koc1352 ,"MultiUpload" 
		String strRootImageTitle[]={"Change Password","E-Card","Add/Edit/Delete Member(s)","Cancel Enrollment","Intimation Details","OPT OUT","ViewFiles"};
		String strNodeImageTitle[]={"Cancel Enrollment","File Upload"};//koc1352 ,"MultiBrowse"

		setRowCount(10);
		setCurrentPage(1);
		setPageLinkCount(10);
		//Setting properties for Root
		Node nodeRoot= new Node();
        nodeRoot.setWidth(55);
        nodeRoot.setMethodName("getName");
        nodeRoot.setNodeMethodName("getMemberList");
        nodeRoot.setIsLink(false);
        nodeRoot.setIcons(createIcons(strRootImagelist,strRootImageTitle));
        setRootSetting(nodeRoot);

        //Setting properties for Node
        Node nodeChild = new Node();
        nodeChild.setWidth(55);
        nodeChild.setMethodName("getName");
        nodeChild.setIcons(createIcons(strNodeImagelist,strNodeImageTitle));
        nodeChild.setTextColor("blue");
        nodeChild.setIsLink(true);
        setChildNodeSetting(nodeChild);

}//end of public void setTreeProperties()
}//end of class OnlineMemberTree

