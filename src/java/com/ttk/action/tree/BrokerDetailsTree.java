package com.ttk.action.tree;

/**
 *
 *
 */
public class BrokerDetailsTree extends Tree{
    public void setTreeProperties()
    {
        String strRootImagelist[]={"AddIcon","EditIcon","ContactsIcon","FeedbackIcon","DeleteIcon"};
        String strNodeImagelist[]={"EditIcon","ContactsIcon","DeleteIcon"};
        String strRootImageTitle[]={"Add new Branch Office","Edit Office","Manage Contacts","Manage Feedback","Delete Office"};
        String strNodeImageTitle[]={"Edit Office","Manage Contacts","Delete Office"};

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

