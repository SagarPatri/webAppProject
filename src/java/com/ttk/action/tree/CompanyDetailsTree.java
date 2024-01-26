/**
 * @ (#) CompanyDetailsTree.java Jan 19, 2006
 * Project      : TTK HealthCare Services
 * File         : CompanyDetailsTree.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jan 19, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.tree;

/**
 *
 *
 */
public class CompanyDetailsTree extends Tree{
    public void setTreeProperties()
    {
        String strRootImagelist[]={"AddIcon","EditIcon","ProductIcon","ContactsIcon","FeedbackIcon","SendConfirmation","DeleteIcon"};
        String strNodeImagelist[]={"EditIcon","ProductIcon","ContactsIcon","DeleteIcon"};
        String strRootImageTitle[]={"Add new Branch Office","Edit Office","Associate Products","Manage Contacts","Manage Feedback","Mail Notification","Delete Office"};
        String strNodeImageTitle[]={"Edit Office","Associate Products","Manage Contacts","Delete Office"};

        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Root
        Node nodeRoot= new Node();
        nodeRoot.setWidth(55);
        nodeRoot.setMethodName("getBranchName");
        nodeRoot.setNodeMethodName("getBranchList");
        nodeRoot.setIcons(createIcons(strRootImagelist,strRootImageTitle));
        setRootSetting(nodeRoot);

        //Setting properties for Node
        Node nodeChild = new Node();
        nodeChild.setWidth(55);
        nodeChild.setMethodName("getBranchName");
        nodeChild.setIcons(createIcons(strNodeImagelist,strNodeImageTitle));
        setChildNodeSetting(nodeChild);
    }//end of public void setTableProperties()
}//end of CompanyDetailsTree.java
