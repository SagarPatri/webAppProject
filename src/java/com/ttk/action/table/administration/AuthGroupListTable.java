/**
 * @ (#) AuthGroupListTable.java   Aug 12, 2008
 * Project        : TTK HealthCare Services
 * File           : AuthGroupListTable.java
 * Author         : Sendhil Kumar V
 * Company        : Span Systems Corporation
 * Date Created   : Aug 12, 2008
 *
 * @author        : Sendhil Kumar V
 * Modified by    : 
 * Modified date  :
 * Reason         : 
 */

package com.ttk.action.table.administration;
import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Auth Group
 * 
 */
public class AuthGroupListTable extends Table
{
   /**
    *  This method creates the columnproperties objects for each and 
    *  every column and adds the column object to the table
    */	
   public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //Setting properties for Plan Name
        Column colGroupName = new Column("Group Name");
        colGroupName.setMethodName("getAuthGrpName");
        colGroupName.setColumnWidth("20%");
        colGroupName.setIsHeaderLink(true);
        colGroupName.setHeaderLinkTitle("Sort by: Group Name");
        colGroupName.setIsLink(true);
        colGroupName.setLinkTitle("Edit Group Name");
        colGroupName.setDBColumnName("GROUP_NAME");
        addColumn(colGroupName);
    }// end of setTableProperties()
}// end of PlanDetailsTable
