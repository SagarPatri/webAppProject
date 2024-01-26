/**
 * @ (#) UserGroupTree.java 12th Jul 2005
 * Project      :
 * File         : UserGroupTree.java
 * Author       : Krishna K H
 * Company      :
 * Date Created : 12th Jul 2005
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.tree;


/**
 * This class provides the information of UserGroupTree Tree
 */
public class UserGroupTree extends Tree
{
	/**
	 * This creates the columnproperties objects for each and
	 * every column and adds the column object to the table
	 */
	public void setTreeProperties()
	{
		String strRootImagelist[]={"AddIcon","EditIcon","ContactsIcon","HospitalNewIcon","DeleteIcon","SendConfirmation"};
		String strNodeImagelist[]={"EditIcon","ContactsIcon","DeleteIcon","SendConfirmation"};
		String strRootImageTitle[]={"Add Office Details","Edit Group Details","Manage Contacts","Provider List","Delete Group","Notification Details"};
		String strNodeImageTitle[]={"Edit Office Details","Manage Contacts","Delete Office","Notification Details"};

		setRowCount(10);
		setCurrentPage(1);
		setPageLinkCount(10);
		//Setting properties for Root
        Node nodeRoot= new Node();
        nodeRoot.setWidth(55);
        nodeRoot.setMethodName("getGroupName");
        nodeRoot.setNodeMethodName("getBranch");
        nodeRoot.setIcons(createIcons(strRootImagelist,strRootImageTitle));
        setRootSetting(nodeRoot);

        Node nodeChild = new Node();
        nodeChild.setWidth(55);
        nodeChild.setMethodName("getGroupName");
        nodeChild.setIcons(createIcons(strNodeImagelist,strNodeImageTitle));
        setChildNodeSetting(nodeChild);

	}//end of public void setTableProperties()
}//end of class UserGroupTree

