/**
 * @ (#) DischargeVoucherTable.java July 11, 2006
 * Project      : TTK HealthCare Services
 * File         : DischargeVoucherTable.java
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : July 11, 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * this class provides the information of discharge voucher table
 */
public class DischargeVoucherTable extends Table
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

        //Setting properties for Ref. No.
        Column colRefNo = new Column("Ref. No.");
        colRefNo.setMethodName("getInvShortfallNo");
        colRefNo.setColumnWidth("50%");
        colRefNo.setIsLink(true);
        colRefNo.setIsHeaderLink(true);
        colRefNo.setLinkTitle("Edit Discharge Voucher");
        colRefNo.setHeaderLinkTitle("Sort by: Ref. No.");
        colRefNo.setDBColumnName("ID");
        addColumn(colRefNo);

        //Setting properties for status
        Column colStatus = new Column("Status");
        colStatus.setMethodName("getStatusDesc");
        colStatus.setColumnWidth("20%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("STATUS");
        addColumn(colStatus);

        //Setting properties for date/time
        Column colDateTime = new Column("Date / Time");
        colDateTime.setMethodName("getDocumentDateTime");
        colDateTime.setColumnWidth("30%");
        colDateTime.setIsHeaderLink(true);
        colDateTime.setHeaderLinkTitle("Sort by: Date / Time");
        colDateTime.setDBColumnName("DATE_TIME");
        addColumn(colDateTime);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }// end of public void setTableProperties()
}// end of DischargeVoucherTable class