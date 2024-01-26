/**
 * @ (#) CircularTable.javaNov 8, 2005
 * Project      : TTK HealthCare Services
 * File         : CircularTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Nov 8, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class CircularTable extends Table 
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
        
        //Setting properties for circular
        Column colNumber = new Column("Circular");
        colNumber.setMethodName("getCircularNumber");
        colNumber.setColumnWidth("70%");
        colNumber.setIsLink(true);
        colNumber.setIsHeaderLink(true);
        colNumber.setHeaderLinkTitle("Sort by Circular Number");
        colNumber.setDBColumnName("CIRCULAR_NUMBER");
        addColumn(colNumber);
        
        //Setting properties for circular date
        Column colDate = new Column("Circular Date ");
        colDate.setMethodName("getRecievedDate");
        colDate.setColumnWidth("30%");
        colDate.setIsHeaderLink(true);
        colDate.setHeaderLinkTitle("Sort by Circular Date");
        colDate.setDBColumnName("CIRCULAR_RECEIVED_DATE");
        addColumn(colDate);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
    }//end of public void setTableProperties()    

}
