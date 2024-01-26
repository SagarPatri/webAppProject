/**
 * @ (#) CourierSearchTable.java May 26th, 2006
 * Project      : TTK HealthCare Services
 * File         : CourierSearchTable.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : May 26th
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.support;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class CourierSearchTable extends Table
{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    public void setTableProperties()
{
    setRowCount(10);
    setCurrentPage(1);
    setPageLinkCount(10);

    //Setting properties for Docket / POD No.
    Column colCourierNo = new Column("Courier No.");
    colCourierNo.setMethodName("getCourierNbr");
    colCourierNo.setColumnWidth("19%");
    colCourierNo.setIsHeaderLink(true);
    colCourierNo.setHeaderLinkTitle("Sort by: Courier No.");
    colCourierNo.setIsLink(true);
    colCourierNo.setLinkTitle("Edit Courier.");
    colCourierNo.setDBColumnName("COURIER_SEQ_ID");
    addColumn(colCourierNo);

    //Setting properties for Docket / POD No.
 //   Column colDocketNo = new Column("Docket / POD No.");
    Column colDocketNo = new Column("Consignment No.");
    colDocketNo.setMethodName("getDocketPODNbr");
    colDocketNo.setColumnWidth("15%");
    colDocketNo.setIsHeaderLink(true);
    colDocketNo.setHeaderLinkTitle("Sort by: Consignment No.");
    colDocketNo.setDBColumnName("ID");
    addColumn(colDocketNo);

    //Setting properties for Courier Name
    Column colCourierName = new Column("Courier Name");
    colCourierName.setMethodName("getCourierName");
    colCourierName.setColumnWidth("25%");
    colCourierName.setIsHeaderLink(true);
    colCourierName.setHeaderLinkTitle("Sort by: Courier Name");
    colCourierName.setDBColumnName("COURIER_COMP_NAME");
    addColumn(colCourierName);

    //Setting properties for Courier Date
    Column colDispatchDate = new Column("Courier Date");
    colDispatchDate.setMethodName("getDocDispatchDate");
    colDispatchDate.setColumnWidth("10%");
    colDispatchDate.setIsHeaderLink(true);
    colDispatchDate.setHeaderLinkTitle("Sort by: Courier Date");
    colDispatchDate.setDBColumnName("DOC_DISPATCH_RCVD_DATE");
    addColumn(colDispatchDate);

    //Setting properties for Vidal Health TPA Branch
    Column colTTKbranch = new Column("Vidal Health Branch");
    colTTKbranch.setMethodName("getOfficeName");
    colTTKbranch.setColumnWidth("26%");
    colTTKbranch.setIsHeaderLink(true);
    colTTKbranch.setHeaderLinkTitle("Sort by: Vidal Health Branch");
    colTTKbranch.setDBColumnName("OFFICE_NAME");
    addColumn(colTTKbranch);


    //Setting properties for check box
    Column colSelect = new Column("Select");
    colSelect.setComponentType("checkbox");
    colSelect.setComponentName("chkopt");
    addColumn(colSelect);

}// end of public void setTableProperties()

}
