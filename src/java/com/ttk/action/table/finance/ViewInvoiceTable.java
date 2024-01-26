/*
 * Created on Nov 9, 2006
 *

/**
 * @ (#) ViewInvoiceTable.java Nov 9, 2006
 * Project      :TTK HealthCare Services
 * File         :ViewInvoiceTable.java
 * Author       :Arun K M
 * Company      :Span Systems Corporation
 * Date Created :Nov 9, 2006
 *
 * @author       :Arun K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Bank details
 *
 */
public class ViewInvoiceTable extends Table{

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Batch Number
        Column colBatchNumber = new Column("Batch Number");
        colBatchNumber.setMethodName("getBatchName");
        colBatchNumber.setColumnWidth("40%");
        colBatchNumber.setIsHeaderLink(true);
        colBatchNumber.setHeaderLinkTitle("Sort by: Batch Number");
        colBatchNumber.setIsLink(true);
        colBatchNumber.setLinkTitle("Edit Batch Number");
        colBatchNumber.setDBColumnName("BATCH_NAME");
        addColumn(colBatchNumber);

       //Setting properties for From Date
        Column colFromDate = new Column("Batch Date");
        colFromDate.setMethodName("getDisplayBatchDate");
        colFromDate.setColumnWidth("30%");
        colFromDate.setIsHeaderLink(true);
        colFromDate.setHeaderLinkTitle("Sort by: FromDate");
        colFromDate.setDBColumnName("BATCH_DATE");
        addColumn(colFromDate);

 //      Setting properties for No. Of Policies
        Column colToDate = new Column("No. Of Schemes");
        colToDate.setMethodName("getNbrPolicy");
        colToDate.setColumnWidth("30%");
        colToDate.setIsHeaderLink(true);
        colToDate.setHeaderLinkTitle("Sort by: ToDate");
        colToDate.setDBColumnName("NO_OF_POLICIES");
        addColumn(colToDate);
   }//end of setTableProperties()

}//end of ViewInvoiceTable


