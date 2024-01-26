/**
 * @ (#) TTKOfficeTree.java Mar 21st, 2006
 * Project      : TTK HealthCare Services
 * File         : TTKOfficeTree.java
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : Mar 21st, 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.tree.administration;

import com.ttk.action.tree.Node;
import com.ttk.action.tree.Tree;

public class TTKOfficeTree extends Tree{
    public void setTreeProperties()
    {
        String strRootImagelist[]={"AddIcon","EditIcon","ClauseIcon","DeleteIcon"};
        String strNodeImagelist[]={"EditIcon","ClauseIcon","DeleteIcon"};
        String strRootImageTitle[]={"Add New Branch Office","Edit Office","Configure Properties","Delete Office"};
        String strNodeImageTitle[]={"Edit Office","Configure Properties","Delete Office"};

        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Root
        Node nodeRoot= new Node();
        nodeRoot.setWidth(55);
        nodeRoot.setMethodName("getOfficeName");
        nodeRoot.setNodeMethodName("getOfficeList");
        nodeRoot.setIcons(createIcons(strRootImagelist,strRootImageTitle));
        setRootSetting(nodeRoot);

        Node nodeChild = new Node();
        nodeChild.setWidth(55);
        nodeChild.setMethodName("getOfficeName");
        nodeChild.setIcons(createIcons(strNodeImagelist,strNodeImageTitle));
        setChildNodeSetting(nodeChild);
    }//end of public void setTableProperties()
}//end of TTKOfficeTree.java
