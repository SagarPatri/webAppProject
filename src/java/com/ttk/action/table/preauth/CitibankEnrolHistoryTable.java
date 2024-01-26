/**
 * @ (#) CitibankEnrolHistoryTable.java 8th Sep 2008
 * Project      	: TTK HealthCare Services
 * File         	: CitibankEnrolHistoryTable.java
 * Author       	: Sendhil Kumar V
 * Company      	: Span Systems Corporation
 * Date Created 	: 8th Sep 2008
 *
 * @author       	: Sendhil Kumar V
 * Modified by   	:
 * Modified date 	:
 * Reason        	:
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Enrollment details
 *
 */
public class CitibankEnrolHistoryTable extends Table
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

        //Setting properties for Order No
        Column colOrderNo = new Column("Order No.");
        colOrderNo.setMethodName("getOrderNbr");
        colOrderNo.setColumnWidth("60%");
        colOrderNo.setIsHeaderLink(true);
        colOrderNo.setHeaderLinkTitle("Sort by: Order No.");
        colOrderNo.setIsLink(true);
        colOrderNo.setLinkTitle("View History");
        colOrderNo.setDBColumnName("ORD_NUM");
        addColumn(colOrderNo);

    }//end of setTableProperties()
}// end of CitibankEnrolHistoryTable
