/*
 * Created on Oct 31, 2006
 *

/**
 * @ (#) paymentbatchtable.java Oct 31, 2006
 * Project      : TTK HealthCare Services
 * File         :paymentbatchtable.java
 * Author       : Arun K M
 * Company      : Span Systems Corporation
 * Date Created : Oct 31, 2006
 *
 * @author       :  Arun K M
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
public class PaymentBatchTable extends Table{

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
        Column colFileName = new Column("File Name");
        colFileName.setMethodName("getFileName");
        colFileName.setColumnWidth("40%");
        colFileName.setIsHeaderLink(true);
        colFileName.setHeaderLinkTitle("Sort by: File Name");
        colFileName.setIsLink(true);
        colFileName.setLinkTitle("Edit File Name");
        colFileName.setDBColumnName("FILE_NAME");
        addColumn(colFileName);
        
        //Setting properties for Batch Number
        Column colBatchNumber = new Column("Batch Number");
        colBatchNumber.setMethodName("getBatchNumber");
        colBatchNumber.setColumnWidth("40%");
        colBatchNumber.setIsHeaderLink(true);
        colBatchNumber.setHeaderLinkTitle("Sort by: Batch Number");
        colBatchNumber.setDBColumnName("BATCH_SEQ_ID");
        addColumn(colBatchNumber);

        //Setting properties for Batch Date
        Column colBatchDate = new Column("Batch Date");
        colBatchDate.setMethodName("getChequeBatchDate");
        colBatchDate.setColumnWidth("20%");
        colBatchDate.setIsHeaderLink(true);
        colBatchDate.setHeaderLinkTitle("Sort by: BatchDate");
        colBatchDate.setDBColumnName("BATCH_DATE");
        addColumn(colBatchDate);


    }//end of setTableProperties()

}//end of paymentbatchtable

