/**
 * @ (#) PackageListTable.java May 5, 2006
 * Project      : TTK HealthCare Services
 * File         : PackageListTable.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : May 5, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *
 *
 */
public class PackageListTable extends Table{

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties Package Name
        Column colPackageName = new Column("Name");
        colPackageName.setMethodName("getTariffItemName");
        colPackageName.setColumnWidth("53%");
        colPackageName.setIsLink(true);
        colPackageName.setIsHeaderLink(true);
        colPackageName.setHeaderLinkTitle("Sort by: Name");
        colPackageName.setDBColumnName("NAME");
        addColumn(colPackageName);
    }//end of setTableProperties()
}//end of PackageListTable.java
