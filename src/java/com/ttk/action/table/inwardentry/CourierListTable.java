/**

* @ (#) CourierListTable.java Jul 26, 2006
* Project       : TTK HealthCare Services
* File          : CourierListTable.java
* Author        : Chandrasekaran J
* Company       : Span Systems Corporation
* Date Created  : Jul 26, 2006

* @author       : Chandrasekaran J
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.table.inwardentry;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class CourierListTable extends Table
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

        //Setting properties for Courier No.
        Column colCourierNo = new Column("Courier No.");
        colCourierNo.setMethodName("getCourierNbr");
        colCourierNo.setColumnWidth("30%");
        colCourierNo.setIsHeaderLink(true);
        colCourierNo.setHeaderLinkTitle("Sort by: Courier No.");
        colCourierNo.setIsLink(true);
        colCourierNo.setLinkTitle("Edit Courier No.");
        colCourierNo.setDBColumnName("COURIER_ID");
        addColumn(colCourierNo);
        
        
        //added for 2koc
        //Setting properties for Courier No.
        Column colConsignmentNo = new Column("Consignment No.");
        colConsignmentNo.setMethodName("getDocketPODNbr");
        colConsignmentNo.setColumnWidth("20%");
        colConsignmentNo.setIsHeaderLink(true);
        colConsignmentNo.setHeaderLinkTitle("Sort by: Consignment No.");
        colConsignmentNo.setDBColumnName("DOCKET_NUMBER");
        addColumn(colConsignmentNo);
        
        //end added for 2koc
        

        //Setting properties for Courier Name
        Column colCourierName = new Column("Courier Name");
        colCourierName.setMethodName("getCourierName");
        colCourierName.setColumnWidth("37%");
        colCourierName.setIsHeaderLink(true);
        colCourierName.setHeaderLinkTitle("Sort by: Courier Name");
        colCourierName.setDBColumnName("COURIER_COMP_NAME");
        addColumn(colCourierName);

        //Setting properties for Courier Date / Time
        Column colDate = new Column("Courier Date / Time ");
        colDate.setMethodName("getDispatchDate");
        colDate.setColumnWidth("13%");
        colDate.setIsHeaderLink(true);
        colDate.setHeaderLinkTitle("Sort by: Courier Date / Time ");
        colDate.setDBColumnName("DOC_DISPATCH_RCVD_DATE");
        addColumn(colDate);
    }// end of setTableProperties()

}//end of CourierListTable class


