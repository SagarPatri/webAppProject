/**
 * @ (#) AuditSearchTable.java May 11th, 2006
 * Project      : TTK HealthCare Services
 * File         : AuditSearchTable.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : May 11th
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.support;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class AuditSearchTable extends Table
{
    private static final long serialVersionUID = 1L;

public void setTableProperties()
{
    setRowCount(10);
    setCurrentPage(1);
    setPageLinkCount(10);

    //Setting properties for Cashless / Claim No.
    Column colPreAuthClaimNo = new Column("Cashless / Claim No.");
    colPreAuthClaimNo.setMethodName("getPreAuthClaimNo");
    colPreAuthClaimNo.setColumnWidth("25%");
    colPreAuthClaimNo.setIsHeaderLink(true);
    colPreAuthClaimNo.setHeaderLinkTitle("Sort by: Cashless / Claim No.");
    colPreAuthClaimNo.setIsLink(true);
    colPreAuthClaimNo.setLinkTitle("Edit Cashless / Claim No");
    colPreAuthClaimNo.setDBColumnName("ID");
    addColumn(colPreAuthClaimNo);

    //Setting properties for Marked Date
    Column colMarkedDate = new Column("Marked Date");
    colMarkedDate.setMethodName("getDocumentDateTime");
    colMarkedDate.setColumnWidth("25%");
    colMarkedDate.setIsHeaderLink(true);
    colMarkedDate.setHeaderLinkTitle("Sort by: Marked Date");
    colMarkedDate.setDBColumnName("MARKED_DATE_TIME");
    addColumn(colMarkedDate);

    //Setting properties for Vidal Health TPA Branch
    Column colTTKbranch = new Column("Vidal Health Branch");
    colTTKbranch.setMethodName("getOfficeName");
    colTTKbranch.setColumnWidth("25%");
    colTTKbranch.setIsHeaderLink(true);
    colTTKbranch.setHeaderLinkTitle("Sort by: Vidal Health Branch");
    colTTKbranch.setDBColumnName("OFFICE_NAME");
    addColumn(colTTKbranch);

    //Setting properties for Status
    Column colStatus = new Column("Status");
    colStatus.setMethodName("getStatusDesc");
    colStatus.setColumnWidth("25%");
    colStatus.setIsHeaderLink(true);
    colStatus.setHeaderLinkTitle("Sort by: Status");
    colStatus.setDBColumnName("STATUS");
    addColumn(colStatus);

}// end of public void setTableProperties()

}
