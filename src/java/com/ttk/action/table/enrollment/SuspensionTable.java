/**
 * @ (#) SuspensionTable.javaFeb 7, 2006
 * Project      : TTK HealthCare Services
 * File         : SuspensionTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Feb 7, 2006
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class SuspensionTable extends Table
{
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(200);
        setCurrentPage(1);
        setPageLinkCount(50);
        
        //Setting properties for From Date.
        Column colFromDate=new Column("From Date");
        colFromDate.setMethodName("getListStartDate");
        colFromDate.setColumnWidth("50%");
        colFromDate.setIsLink(true);
        colFromDate.setIsHeaderLink(true);
        colFromDate.setHeaderLinkTitle("Sort by: From Date");
        colFromDate.setDBColumnName("MEM_SUSPEND_FROM");
        addColumn(colFromDate);
        
        //Setting properties for To date
        Column colToDate = new Column("To Date ");
        colToDate.setMethodName("getListEndDate");
        colToDate.setColumnWidth("50%");
        colToDate.setIsHeaderLink(true);
        colToDate.setHeaderLinkTitle("Sort by: To Date");
        colToDate.setDBColumnName("MEM_SUSPEND_TO");
        addColumn(colToDate);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
    }// end of setTableProperties()
}// end of SuspensionTable class
