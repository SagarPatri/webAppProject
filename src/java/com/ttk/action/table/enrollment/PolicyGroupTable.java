/**
 * @ (#) PolicyGroupTable.javaFeb 2, 2006
 * Project      : TTK HealthCare Services
 * File         : PolicyGroupTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Feb 2, 2006
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class PolicyGroupTable extends Table
{
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        
      //Setting properties for Group Id.
        Column colGroupId=new Column("Group Id");
        colGroupId.setMethodName("getGroupID");
        colGroupId.setColumnWidth("20%");
        colGroupId.setIsLink(true);
        colGroupId.setIsHeaderLink(true);
        colGroupId.setHeaderLinkTitle("Sort by: Group Id.");
        colGroupId.setDBColumnName("GROUP_ID");
        addColumn(colGroupId);
        

        //Setting properties for Group Name 
        Column colGroupName=new Column("Group Name");
        colGroupName.setMethodName("getGroupName");
        colGroupName.setColumnWidth("40%");
        colGroupName.setIsHeaderLink(true);
        colGroupName.setHeaderLinkTitle("Sort by: Group Name.");
        colGroupName.setDBColumnName("GROUP_NAME");
        addColumn(colGroupName);
        
        //Setting properties forVidal Health TPABranch.
        Column colTtkBranch=new Column("Branch Location");
        colTtkBranch.setMethodName("getBranchName");
        colTtkBranch.setColumnWidth("20%");
        colTtkBranch.setIsHeaderLink(true);
        colTtkBranch.setHeaderLinkTitle("Sort by: Vidal Health Branch.");
        colTtkBranch.setDBColumnName("OFFICE_NAME");
        addColumn(colTtkBranch);
    }// end of setTableProperties() 
}//end of PolicyGroupTable.java
