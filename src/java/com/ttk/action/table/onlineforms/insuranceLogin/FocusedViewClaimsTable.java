package com.ttk.action.table.onlineforms.insuranceLogin;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class FocusedViewClaimsTable extends Table{

	
	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	
	@Override
	public void setTableProperties() {

		setRowCount(3);
        setCurrentPage(1);
        setPageLinkCount(2);

        //Setting properties for colClaimNo
        Column colClaimNo = new Column("Claim No");
        colClaimNo.setMethodName("getClaimNbr");
        colClaimNo.setColumnWidth("20%");
        colClaimNo.setIsLink(true);
        colClaimNo.setDBColumnName("CLAIM_NUMBER");
        addColumn(colClaimNo);

        //Setting properties for Date From 
        Column colDateFrom = new Column("Date From");
        colDateFrom.setMethodName("getDateFrom");
        colDateFrom.setColumnWidth("15%");
        colDateFrom.setDBColumnName("DATE_OF_HOSPITALIZATION");
        addColumn(colDateFrom);
        
        //Setting properties for Date To
        Column colDateTo = new Column("Date To");
        colDateTo.setMethodName("getDateTo");
        colDateTo.setColumnWidth("15%");
        colDateTo.setDBColumnName("DATE_OF_DISCHARGE");
        addColumn(colDateTo);
        
        //Setting properties for Claimed Amt
        Column colClaimedAmt = new Column("Claimed Amt");
        colClaimedAmt.setMethodName("getClaimedAmt");
        colClaimedAmt.setColumnWidth("10%");
        colClaimedAmt.setDBColumnName("TOT_DISC_GROSS_AMOUNT");
        addColumn(colClaimedAmt);
        

        //Setting properties for colSettledAmt
        Column colSettledAmt = new Column("Settled Amt");
        colSettledAmt.setMethodName("getSettledAmt");
        colSettledAmt.setColumnWidth("10%");
        colSettledAmt.setDBColumnName("TOT_APPROVED_AMOUNT");
        addColumn(colSettledAmt);
          
          
        //Setting properties for Claimed Amt
        Column colClaimedStatus = new Column("Status");
        colClaimedStatus.setMethodName("getStatus");
        colClaimedStatus.setColumnWidth("10%");
        colClaimedStatus.setDBColumnName("CLM_STATUS_TYPE_ID");
        addColumn(colClaimedStatus);
        
	}

}
