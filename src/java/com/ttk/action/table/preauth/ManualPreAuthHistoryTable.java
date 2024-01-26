package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ManualPreAuthHistoryTable extends Table
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

        //Setting properties for Cashless No
        Column colpreAuthNo = new Column("Cashless No.");
        colpreAuthNo.setMethodName("getPreAuthNo");
        colpreAuthNo.setColumnWidth("17%");
        colpreAuthNo.setIsHeaderLink(true);
        colpreAuthNo.setHeaderLinkTitle("Sort by: Cashless No.");
        colpreAuthNo.setIsLink(true);
        colpreAuthNo.setLinkTitle("View History");
        colpreAuthNo.setDBColumnName("PRE_AUTH_NUMBER");
        addColumn(colpreAuthNo);

        //Setting properties for Member Name
        Column colClaimantName = new Column("Member Name");
        colClaimantName.setMethodName("getClaimantName");
        colClaimantName.setColumnWidth("16%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("CLAIMANT_NAME");
        addColumn(colClaimantName);

        //Setting properties for Relationship
        Column colRelationShip =new Column("Relationship");
        colRelationShip.setMethodName("getRelationshipDesc");
        colRelationShip.setColumnWidth("16%");
        colRelationShip.setIsHeaderLink(true);
        colRelationShip.setHeaderLinkTitle("Sort by: Relationship");
        colRelationShip.setDBColumnName("RELATION");
        addColumn(colRelationShip);

        //Setting properties for Approved Amt. (Rs)
        Column colAppAmt =new Column("Approved Amt. (Rs)");
        colAppAmt.setMethodName("getApprovedAmount");
        colAppAmt.setColumnWidth("18%");
        colAppAmt.setIsHeaderLink(true);
        colAppAmt.setHeaderLinkTitle("Sort by: Approved Amt. (Rs)");
        colAppAmt.setDBColumnName("TOTAL_APP_AMOUNT");
        addColumn(colAppAmt);

        //Setting properties for Status
        Column colStatus =new Column("Status");
        colStatus.setMethodName("getStatusDesc");
        colStatus.setColumnWidth("8%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("STATUS_DESC");
        addColumn(colStatus);

    }//end of setTableProperties()
}//end of ManualHistoryTable class
