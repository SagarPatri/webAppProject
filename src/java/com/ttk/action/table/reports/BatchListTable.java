package com.ttk.action.table.reports;


import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class BatchListTable extends Table
{
	/**
	 *  This method creates the columnproperties objects for each and 
	 *  every column and adds the column object to the table
	 */	
	   public void setTableProperties()
	    {
	        setRowCount(10);
	        setCurrentPage(1);
	        setPageLinkCount(10);
	        
	        //Setting properties for Batch Number
	        Column colBatchNo = new Column("Batch No.");
	        colBatchNo.setMethodName("getBatchNbr");
	        colBatchNo.setColumnWidth("50%");
	        colBatchNo.setIsHeaderLink(true);
	        colBatchNo.setHeaderLinkTitle("Sort by:Batch No. ");
	        colBatchNo.setIsLink(true);
	        colBatchNo.setLinkTitle("Edit Batch No");
	        colBatchNo.setDBColumnName("BATCH_NUMBER");
	        addColumn(colBatchNo);
	        
	        //Setting properties for Batch Date
	        Column colBatchDate =new Column("Batch Date");
	        colBatchDate.setMethodName("getBatchRecdDate");
	        colBatchDate.setColumnWidth("50%");
	        colBatchDate.setIsHeaderLink(true);
	        colBatchDate.setHeaderLinkTitle("Sort by:Batch Date");
	        colBatchDate.setDBColumnName("batch_date");    
	        addColumn(colBatchDate);
	    }//end of setTableProperties()
}//end of BatchListTable
