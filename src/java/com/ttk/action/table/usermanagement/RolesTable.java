/**
 * @ (#) RolesTable.javaDec 28, 2005
 * Project      : TTK HealthCare Services
 * File         : RolesTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Dec 28, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.usermanagement;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * this class provides the information of roles table
 */
public class RolesTable extends Table
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

        //Setting properties for roles
        Column colRoles = new Column("Roles");
        colRoles.setMethodName("getRoleName");
        colRoles.setColumnWidth("30%");
        colRoles.setIsLink(true);
        colRoles.setIsHeaderLink(true);
        colRoles.setHeaderLinkTitle("Sort by: Roles");
        colRoles.setDBColumnName("ROLE_NAME");
        addColumn(colRoles);

        //Setting properties for description
        Column colDescription = new Column("Description");
        colDescription.setMethodName("getRoleDesc");
        colDescription.setColumnWidth("50%");
        colDescription.setIsHeaderLink(true);
        colDescription.setHeaderLinkTitle("Sort by: Description");
        colDescription.setDBColumnName("ROLE_DESC");
        addColumn(colDescription);

        //Setting properties for user type
        Column colUserType = new Column("User Type");
        colUserType.setMethodName("getUserType");
        colUserType.setColumnWidth("20%");
        colUserType.setIsHeaderLink(true);
        colUserType.setHeaderLinkTitle("Sort by: User Type");
        colUserType.setDBColumnName("DESCRIPTION");
        addColumn(colUserType);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
    }// end of public void setTableProperties()
}// end of RolesTable class
