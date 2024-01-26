/**
 * @ (#) added as per KOC 1179  BULK PDF GEneration
 * /
 */

package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the of Bulk PDFs Generated KOC 1179
 *
 */
public class BulkPDFTable extends Table{

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for File Name
        Column colFileName = new Column("File Name.");
        colFileName.setMethodName("getFileName");
        colFileName.setColumnWidth("40%");
        colFileName.setIsHeaderLink(true);
        colFileName.setHeaderLinkTitle("Sort by: File Name.");
        colFileName.setIsLink(true);
        colFileName.setLinkTitle("View File");
        colFileName.setDBColumnName("BATCH_FILE_NAME");
        addColumn(colFileName);
        
        //Setting properties for Batch Number
        Column colBatchNumber =new Column("Batch Number");
        colBatchNumber.setMethodName("getBatchNumber");
        colBatchNumber.setColumnWidth("35%");
        colBatchNumber.setIsHeaderLink(true);
        colBatchNumber.setHeaderLinkTitle("Sort by: Batch No.");
       colBatchNumber.setDBColumnName("BATCH_NUMBER");
        addColumn(colBatchNumber);
        
        
        //Setting properties for BatchDate 
        Column colBatchDate = new Column("Batch  Date.");
        colBatchDate.setMethodName("getBatchDate1");
        colBatchDate.setColumnWidth("25%");
        colBatchDate.setIsHeaderLink(true);
        colBatchDate.setHeaderLinkTitle("Sort by: Batch  Date.");
        colBatchDate.setDBColumnName("BATCH_DATE");
        addColumn(colBatchDate);

        /*//Setting properties for Hospital Name
        Column colHospName =new Column("Member Name");
        colHospName.setMethodName("getClaimantName");
        colHospName.setColumnWidth("15%");
        colHospName.setIsHeaderLink(true);
        colHospName.setHeaderLinkTitle("Sort by: Member Name");
        colHospName.setDBColumnName("CLAIMANT_NAME");
        addColumn(colHospName);*/

       /* //Setting properties for Approved Amt. (Rs)
        Column colAppAmt =new Column("Settlement Amt. (Rs)");
        colAppAmt.setMethodName("getApprovedAmount");
        colAppAmt.setColumnWidth("10%");
        colAppAmt.setIsHeaderLink(true);
        colAppAmt.setHeaderLinkTitle("Sort by: Settlement Amt. (Rs)");
        colAppAmt.setDBColumnName("TOTAL_APP_AMOUNT");
        addColumn(colAppAmt);

        //Setting properties for Received Date / Time
        Column colRecDate =new Column("Received Date / Time");
        colRecDate.setMethodName("getPreAuthReceivedDate");
        colRecDate.setColumnWidth("21%");
        colRecDate.setIsHeaderLink(true);
        colRecDate.setHeaderLinkTitle("Sort by: Received Date / Time");
        colRecDate.setDBColumnName("RCVD_DATE");
        addColumn(colRecDate);
        
        //Setting properties for Admission Date / Time
        Column colAdmDate =new Column("Admission Date / Time");
        colAdmDate.setMethodName("getClaimAdmissionDateTime");
        colAdmDate.setColumnWidth("17%");
        colAdmDate.setIsHeaderLink(true);
        colAdmDate.setHeaderLinkTitle("Sort by: Admission Date / Time");
        colAdmDate.setDBColumnName("DATE_OF_ADMISSION");
        addColumn(colAdmDate);
        
        //Setting properties for Discharge Date / Time
        Column colDischargeDate =new Column("Discharge Date / Time");
        colDischargeDate.setMethodName("getClmDischargeDateTime");
        colDischargeDate.setColumnWidth("17%");
        colDischargeDate.setIsHeaderLink(true);
        colDischargeDate.setHeaderLinkTitle("Sort by: Discharge Date / Time");
        colDischargeDate.setDBColumnName("DATE_OF_DISCHARGE");
        addColumn(colDischargeDate);
        
        //Setting properties for Status
        Column colStatus =new Column("Status");
        colStatus.setMethodName("getRemarks");
        colStatus.setColumnWidth("8%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("DESCRIPTION");
        addColumn(colStatus);

*/    }//end of setTableProperties()

}//end of ClaimTable
