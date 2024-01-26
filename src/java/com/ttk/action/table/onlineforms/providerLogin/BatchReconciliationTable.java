package com.ttk.action.table.onlineforms.providerLogin; 

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class BatchReconciliationTable extends Table{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	
	@Override
	public void setTableProperties() {

		setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        
        //Setting properties for Date
        Column colDate = new Column("Date");//0
        colDate.setMethodName("getBatchDate");
        colDate.setColumnWidth("15%");
        colDate.setDBColumnName("BATCH_RECEIVED_DATE");
        addColumn(colDate);
        
      //Setting properties for colBatchNumber
        Column colBatchNumber  = new Column("Batch Number");//1
        colBatchNumber.setMethodName("getBatchNumber");
        colBatchNumber.setColumnWidth("25%");
        colBatchNumber.setDBColumnName("BATCH_NUMBER");
        addColumn(colBatchNumber);
        
        
        //Setting properties for NO_OF_INVOICES
        Column colNoOfInv = new Column("No.Of Invoices");//2
        colNoOfInv.setMethodName("getNoOfInvs");
        colNoOfInv.setColumnWidth("10%");
        colNoOfInv.setIsLink(true);
        colNoOfInv.setDBColumnName("No_OF_INVOICES");
        addColumn(colNoOfInv);
        
        
      //Setting properties for Claimed Amount
        Column colClaimedAmount = new Column("Claimed Amount");//3
        colClaimedAmount.setMethodName("getClaimedAmt");
        colClaimedAmount.setColumnWidth("15%");
        colClaimedAmount.setDBColumnName("CLAIMED_AMT");
        addColumn(colClaimedAmount);
        
      //Setting properties for UNder Process
        Column colUnderProcess = new Column("Under Process");//4
        colUnderProcess.setMethodName("getUnderProcess");
        colUnderProcess.setColumnWidth("10%");
        colUnderProcess.setIsLink(true);
        colUnderProcess.setLinkParamName("SecondLink");
        colUnderProcess.setDBColumnName("INPROGRESS");
        addColumn(colUnderProcess);
        
      //Setting properties for APPROVED
        Column colApproved = new Column("Approved");//5
        colApproved.setMethodName("getApproved");
        colApproved.setColumnWidth("10%");
        colApproved.setIsLink(true);
        colApproved.setLinkParamName("ThirdLink");
        colApproved.setDBColumnName("APPROVED");
        addColumn(colApproved);
        
      //Setting properties for REJECTED
        Column colRejected = new Column("Rejected");//6
        colRejected.setMethodName("getRejected");
        colRejected.setColumnWidth("10%");
        colRejected.setIsLink(true);
        colRejected.setLinkParamName("FourthLink");
        colRejected.setDBColumnName("REJECTED");
        addColumn(colRejected);
        
      //Setting properties for SHORTFALL
        Column colShortFall = new Column("ShortFall");//7
        colShortFall.setMethodName("getShortFall");
        colShortFall.setColumnWidth("15%");
        colShortFall.setIsLink(true);
        colShortFall.setLinkParamName("FifthLink");
        colShortFall.setDBColumnName("SHORTFALL");
        colShortFall.setIsLink(true);
        addColumn(colShortFall);
        
      //Setting properties for CLOSED
        Column colClosed = new Column("Closed");//8
        colClosed.setMethodName("getClosed");
        colClosed.setColumnWidth("20%");
        colClosed.setIsLink(true);
        colClosed.setLinkParamName("SixthLink");
        colClosed.setDBColumnName("CLOSED");
		addColumn(colClosed);

	}

}
