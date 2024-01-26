/**
 * @ (#) EndorsementSummaryTable.java August 3rd, 2006
 * Project      : TTK HealthCare Services
 * File         : EndorsementSummaryTable.java
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


public class EndorsementSummaryTable extends Table

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

        //Setting properties for ID
        Column colId = new Column("Id");
        colId.setMethodName("getEnrollmentNbr");
        colId.setColumnWidth("15%");
        colId.setIsHeaderLink(true);
        colId.setHeaderLinkTitle("Sort by: Id.");
        //colID.setIsLink(true);
        colId.setLinkTitle("Edit Batch");
        colId.setDBColumnName("ENROLLMENT_NUMBER");
        addColumn(colId);

        //Setting properties for Employee Name
        Column colEmployeeName = new Column("Employee Name");
        colEmployeeName.setMethodName("getMemberName");
        colEmployeeName.setColumnWidth("15%");
        colEmployeeName.setIsHeaderLink(true);
        colEmployeeName.setHeaderLinkTitle("Sort by: Employee Name.");
        colEmployeeName.setDBColumnName("BENEFICIARY_NAME");
        addColumn(colEmployeeName);

        //Setting properties for Endorsement No.
        Column colEndorsementNo = new Column("Endorsement No");
        colEndorsementNo.setMethodName("getEndorsementNbr");
        colEndorsementNo.setColumnWidth("15%");
        colEndorsementNo.setIsHeaderLink(true);
        colEndorsementNo.setHeaderLinkTitle("Sort by: Endorsement No.");
        colEndorsementNo.setDBColumnName("CUSTOMER_ENDORSEMENT_NUMBER");
        addColumn(colEndorsementNo);

        //Setting properties for Endorsement Date
        Column colEndorsementDate = new Column("Endorsement Date");
        colEndorsementDate.setMethodName("getEffectiveDate");
        colEndorsementDate.setColumnWidth("20%");
        colEndorsementDate.setIsHeaderLink(true);
        colEndorsementDate.setHeaderLinkTitle("Sort by: Endorsement Date.");
        colEndorsementDate.setDBColumnName("ENDORSEMENT_DATE");
        addColumn(colEndorsementDate);

        /*//Setting properties for No. of Lives Added
        Column colLivesAdded = new Column("No. of Lives Added");
        colLivesAdded.setMethodName("");
        colLivesAdded.setColumnWidth("15%");
        colLivesAdded.setIsHeaderLink(true);
        colLivesAdded.setHeaderLinkTitle("Sort by: No. of Lives Added");
        colLivesAdded.setDBColumnName("");
        addColumn(colLivesAdded);

        //Setting properties for No. of Lives Deleted
        Column colLivesDeleted = new Column("No. of Lives Deleted");
        colLivesDeleted.setMethodName("");
        colLivesDeleted.setColumnWidth("20%");
        colLivesDeleted.setIsHeaderLink(true);
        colLivesDeleted.setHeaderLinkTitle("Sort by: No. of Lives Deleted");
        colLivesDeleted.setDBColumnName("");
        addColumn(colLivesDeleted);

        //Setting properties for No. of Lives Modified
        Column colLivesModified = new Column("No. of Lives Modified");
        colLivesModified.setMethodName("");
        colLivesModified.setColumnWidth("20%");
        colLivesModified.setIsHeaderLink(true);
        colLivesModified.setHeaderLinkTitle("Sort by: No. of Lives Modified");
        colLivesModified.setDBColumnName("");
        addColumn(colLivesModified);*/

    }//end of public void setTableProperties()

}//end of EndorsementSummaryTable class
