/**
 * @ (#) BatchDetailsTable.java   July 10, 2008
 * Project        : TTK HealthCare Services
 * File           : BatchDetailsTable.java
 * Author         : Sanjay.G.Nayaka
 * Company        : Span Systems Corporation
 * Date Created   : July 10, 2008
 *
 * @author        : Sanjay.G.Nayaka
 * Modified by    : 
 * Modified date  :
 * Reason         : 
 */

package com.ttk.action.table.reports;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class BatchDetailsTable extends Table{

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
	        Column colBatchNo = new Column("Batch No");
	        colBatchNo.setMethodName("getBatchNo");
	        colBatchNo.setColumnWidth("15%");
	        colBatchNo.setIsHeaderLink(true);
	        colBatchNo.setHeaderLinkTitle("Sort by:Batch No ");
	        colBatchNo.setIsLink(true);
	        colBatchNo.setLinkTitle("Edit Batch No");
	        colBatchNo.setDBColumnName("BATCH_NUMBER");
	        addColumn(colBatchNo);
	        
	        //Setting properties for Float Account Number
	        Column colAccNo = new Column("Float Account No");
	        colAccNo.setMethodName("getFloatAccNo");
	        colAccNo.setColumnWidth("10%");
	        colAccNo.setIsHeaderLink(true);
	        colAccNo.setHeaderLinkTitle("Sort by:Float Account No");
	        colAccNo.setDBColumnName("FLOAT_ACCT_NUM");
	        addColumn(colAccNo);
	        
	        //Setting properties for Batch Date
	        Column colBatchDate =new Column("BatchDate");
	        colBatchDate.setMethodName("getEFTBatchDate");
	        colBatchDate.setColumnWidth("10%");
	        colBatchDate.setIsHeaderLink(true);
	        colBatchDate.setHeaderLinkTitle("Sort by:BatchDate");
	        colBatchDate.setDBColumnName("BATCH_DATE");    
	        addColumn(colBatchDate);
	    }//end of setTableProperties()
}//end of BatchDetailsTable  
