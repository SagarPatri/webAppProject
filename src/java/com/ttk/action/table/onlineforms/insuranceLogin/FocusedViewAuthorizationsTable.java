
package com.ttk.action.table.onlineforms.insuranceLogin;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class FocusedViewAuthorizationsTable extends Table{

	
	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	
	@Override
	public void setTableProperties() {

		setRowCount(3);
        setCurrentPage(1);
        setPageLinkCount(2);

        Column colAuthName = new Column("Name");
        colAuthName.setMethodName("getName");
        colAuthName.setColumnWidth("20%");
        colAuthName.setDBColumnName("MEM_NAME");
        addColumn(colAuthName);

        Column colRelationship = new Column("Relationship");
        colRelationship.setMethodName("getRelation");
        colRelationship.setColumnWidth("10%");
        colRelationship.setDBColumnName("RELSHIP_TYPE_ID");
        addColumn(colRelationship);
        
        Column colAuthNo = new Column("Pre-Auth No");
        colAuthNo.setMethodName("getAuthNo");
        colAuthNo.setIsLink(true);
        colAuthNo.setLinkParamName("SecondLink");
        colAuthNo.setColumnWidth("20%");
        colAuthNo.setDBColumnName("AUTH_NUMBER");
        addColumn(colAuthNo);
        
        //Setting properties for Date From 
        Column colDateFrom = new Column("Date From");
        colDateFrom.setMethodName("getDateFrom");
        colDateFrom.setColumnWidth("10%");
        colDateFrom.setDBColumnName("HOSPITALIZATION_DATE");
        addColumn(colDateFrom);
        
        //Setting properties for Date To
        Column colDateTo = new Column("Date To");
        colDateTo.setMethodName("getDateTo");
        colDateTo.setColumnWidth("10%");
        colDateTo.setDBColumnName("DISCHARGE_DATE");
        addColumn(colDateTo);
        
        Column colClaimedAmt = new Column("Claimed Amt");
        colClaimedAmt.setMethodName("getClaimedAmt");
        colClaimedAmt.setColumnWidth("10%");
        colClaimedAmt.setDBColumnName("TOT_DISC_GROSS_AMOUNT");
        addColumn(colClaimedAmt);
        
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
