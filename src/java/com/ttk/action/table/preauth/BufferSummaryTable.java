/**
 * @ (#) BufferSummaryTable.java August 3rd, 2006
 * Project      : TTK HealthCare Services
 * File         : BufferSummaryTable.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : August 3rd
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class BufferSummaryTable extends Table {

    /**

     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Buffer Request No.
        Column colBufferRequestNo= new Column("Buffer Request No.");
        colBufferRequestNo.setMethodName("getBufferNbr");
        colBufferRequestNo.setColumnWidth("17%");
        colBufferRequestNo.setIsHeaderLink(true);
        colBufferRequestNo.setHeaderLinkTitle("Sort by: Buffer Request No.");
        //colBufferRequestNo.setIsLink(true);
        colBufferRequestNo.setLinkTitle("Edit Buffer Request No");
        colBufferRequestNo.setDBColumnName("BUFFER_REQ_NO");
        addColumn(colBufferRequestNo);

        //Setting properties for Enrollment Id
        Column colEnrollmentId = new Column("Enrollment Id.");
        colEnrollmentId.setMethodName("getEnrollmentID");
        colEnrollmentId.setColumnWidth("14%");
        colEnrollmentId.setIsHeaderLink(true);
        colEnrollmentId.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_NUMBER");
        addColumn(colEnrollmentId);

        //Setting properties for Member Name
        Column colClaimantName = new Column("Member Name");
        colClaimantName.setMethodName("getClaimantName");
        colClaimantName.setColumnWidth("15%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("BENEFICIARY_NAME");
        addColumn(colClaimantName);

        //Setting properties for Requested Date / Time
        Column colRequestedDateTime = new Column("Requested Date / Time");
        colRequestedDateTime.setMethodName("getRequestedDate");
        colRequestedDateTime.setColumnWidth("15%");
        colRequestedDateTime.setIsHeaderLink(true);
        colRequestedDateTime.setHeaderLinkTitle("Sort by: Requested Date / Time");
        colRequestedDateTime.setDBColumnName("BUFFER_REQ_DATE");
        addColumn(colRequestedDateTime);

        //Setting properties for Requested Amt.(Rs)
        Column colRequestedAmt = new Column("Requested Amt.(Rs)");
        colRequestedAmt.setMethodName("getRequestedAmt");
        colRequestedAmt.setColumnWidth("17%");
        colRequestedAmt.setIsHeaderLink(true);
        colRequestedAmt.setHeaderLinkTitle("Sort by: Requested Amt.(Rs)");
        colRequestedAmt.setDBColumnName("BUFFER_REQ_AMOUNT");
        addColumn(colRequestedAmt);

        //Setting properties for  Status
        Column colStatus = new Column("Status");
        colStatus.setMethodName("getRemarks");
        colStatus.setColumnWidth("12%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by:  Status");
        colStatus.setDBColumnName("DESCRIPTION");
        addColumn(colStatus);

        //Setting properties for Approved Amt.(Rs)
        Column colApprovedAmt = new Column("Approved Amt.(Rs)");
        colApprovedAmt.setMethodName("getApprovedAmt");
        colApprovedAmt.setColumnWidth("30%");
        colApprovedAmt.setIsHeaderLink(true);
        colApprovedAmt.setHeaderLinkTitle("Sort by: Approved Amt.(Rs)");
        colApprovedAmt.setDBColumnName("BUFFER_APP_AMOUNT");
        addColumn(colApprovedAmt);

    }//end of public void setTableProperties()

}//end of BufferSummaryTable class
