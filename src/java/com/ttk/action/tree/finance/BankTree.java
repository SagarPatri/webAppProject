/**
 * @ (#) BankTree.java 26th June 2006
 * Project      :
 * File         : BankTree.java
 * Author       : Raghavendra T M
 * Company      :
 * Date Created : 26th June 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.tree.finance;

import com.ttk.action.tree.Node;
import com.ttk.action.tree.Tree;


/**
 * This class provides the information of BankTree Tree
 */
public class BankTree extends Tree
{
	/**
	 * This creates the columnproperties objects for each and
	 * every column and adds the column object to the table
	 */
	public void setTreeProperties()
	{
		String strRootImagelist[]={"AddIcon","EditIcon","ContactsIcon"};
		String strNodeImagelist[]={"EditIcon","ContactsIcon","DeleteIcon"};
		String strRootImageTitle[]={"Add Branch Details","Edit Bank Details","Manage Contacts"};
		String strNodeImageTitle[]={"Edit Bank Details","Manage Contacts","Delete Branch Office"};

		setRowCount(10);
		setCurrentPage(1);
		setPageLinkCount(10);
		//Setting properties for Root
        Node nodeRoot= new Node();
        nodeRoot.setWidth(55);
        nodeRoot.setMethodName("getBankName");
        nodeRoot.setNodeMethodName("getBranch");
        nodeRoot.setIcons(createIcons(strRootImagelist,strRootImageTitle));
        setRootSetting(nodeRoot);

        Node nodeChild = new Node();
        nodeChild.setWidth(55);
        nodeChild.setMethodName("getBankName");
        nodeChild.setIcons(createIcons(strNodeImagelist,strNodeImageTitle));
        setChildNodeSetting(nodeChild);

	}//end of public void setTableProperties()
}//end of class BankTree

