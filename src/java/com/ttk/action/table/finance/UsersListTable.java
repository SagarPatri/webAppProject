/**
 * @ (#) UsersListTable.java June 15, 2006
 * Project      : TTK HealthCare Services
 * File         : UsersListTable.java
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : June 15, 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * this class provides the information of Transaction Table
 */
public class UsersListTable extends Table
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

        //Setting properties for Transaction No.
        Column colUserName = new Column("User Name");
        colUserName.setMethodName("getUserName");
        colUserName.setColumnWidth("35%");
        colUserName.setIsLink(true);
        colUserName.setIsHeaderLink(true);
        colUserName.setHeaderLinkTitle("Sort by: User Name");
        colUserName.setDBColumnName("CONTACT_NAME");
        addColumn(colUserName);

        //Setting properties for Type
        Column colUserID = new Column("User ID");
        colUserID.setMethodName("getUserID");
        colUserID.setColumnWidth("20%");
        colUserID.setIsHeaderLink(true);
        colUserID.setHeaderLinkTitle("Sort by: User ID");
        colUserID.setDBColumnName("USER_ID");
        addColumn(colUserID);

        //Setting properties for Date
        Column colRole = new Column("Role");
        colRole.setMethodName("getRoleName");
        colRole.setColumnWidth("20%");
        colRole.setIsHeaderLink(true);
        colRole.setHeaderLinkTitle("Sort by: Role");
        colRole.setDBColumnName("ROLE_NAME");
        addColumn(colRole);

        //Setting properties for Amount (Rs)
        Column colTTKBranch = new Column("Vidal Health Branch");
        colTTKBranch.setMethodName("getName");
        colTTKBranch.setColumnWidth("25%");
        colTTKBranch.setIsHeaderLink(true);
        colTTKBranch.setHeaderLinkTitle("Sort by: Vidal Health Branch");
        colTTKBranch.setDBColumnName("OFFICE_NAME");
        addColumn(colTTKBranch);

    }// end of public void setTableProperties()
}// end of UsersListTable Class