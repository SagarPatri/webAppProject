/**
 * @ (#) TDSCategoryTree.java July 28th, 2009
 * Project      : TTK HealthCare Services
 * File         : TDSCategoryTree.java
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : July 28th, 2009
 *
 * @author       : Balakrishna Erram
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.tree.administration;

import com.ttk.action.tree.Node;
import com.ttk.action.tree.Tree;

public class TDSCategoryTree extends Tree{
    public void setTreeProperties()
    {
        String strRootImagelist[]={"AddIcon"};
        String strNodeImagelist[]={"EditIcon","ClauseIcon"};
        String strRootImageTitle[]={"Add New Sub Category"};
        String strNodeImageTitle[]={"Edit Sub Category","Define Tax Percentage"};

        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Root
        Node nodeRoot= new Node();
        nodeRoot.setWidth(55);
        nodeRoot.setMethodName("getTdsCatName");
        nodeRoot.setNodeMethodName("getTDSConfList");
        nodeRoot.setIcons(createIcons(strRootImagelist,strRootImageTitle));
        setRootSetting(nodeRoot);

        Node nodeChild = new Node();
        nodeChild.setWidth(55);
        nodeChild.setMethodName("getTdsSubCatName");
        nodeChild.setIcons(createIcons(strNodeImagelist,strNodeImageTitle));
        setChildNodeSetting(nodeChild);
    }//end of public void setTableProperties()
}//end of TDSCategoryTree.java
