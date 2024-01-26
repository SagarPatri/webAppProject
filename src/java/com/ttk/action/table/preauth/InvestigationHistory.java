package com.ttk.action.table.preauth;
import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class InvestigationHistory extends Table{
	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for History
        Column colInvestigationNo = new Column("Investigation No.");
        colInvestigationNo.setMethodName("getInvestigationNo");
        colInvestigationNo.setColumnWidth("17%");
        colInvestigationNo.setIsHeaderLink(false);
        colInvestigationNo.setHeaderLinkTitle("Sort by: Investigation No.");
        colInvestigationNo.setIsLink(true);
        colInvestigationNo.setLinkTitle("View History");
        colInvestigationNo.setDBColumnName("INVESTIGATION_ID");  
        addColumn(colInvestigationNo);

        //Setting properties for Authorization No
        Column colInvestigationDate =new Column("Investigation Date");
  //      colInvestigationDate.setMethodName("getHistoryInvestDate");// getInvestDate    getHistoryInvestDate  koc11 koc 11  getInvestDateTime getInvestDateTime
        colInvestigationDate.setMethodName("getInvestDateTime");
        colInvestigationDate.setColumnWidth("16%");
        colInvestigationDate.setIsHeaderLink(false);
        colInvestigationDate.setHeaderLinkTitle("Sort by: Investigation Date");
        colInvestigationDate.setDBColumnName("INVESTIGATED_DATE");
        addColumn(colInvestigationDate);
        
        //Setting properties for Hospital Name
        Column colInvestigationAmt =new Column("Investigation Amt");
        colInvestigationAmt.setMethodName("getHistoryClaimAmt");
        colInvestigationAmt.setColumnWidth("16%");
        colInvestigationAmt.setIsHeaderLink(false);
        colInvestigationAmt.setHeaderLinkTitle("Sort by: Investigation Amt");
        colInvestigationAmt.setDBColumnName("CLM_APR_AMT");
        addColumn(colInvestigationAmt);

        //Setting properties for Approved Amt. (Rs)
        Column colInvestigationBy =new Column("Investigation By");
        colInvestigationBy.setMethodName("getInvestigatedBy");
        colInvestigationBy.setColumnWidth("18%");
        colInvestigationBy.setIsHeaderLink(false);
        colInvestigationBy.setHeaderLinkTitle("Sort by: Investigation By");
        colInvestigationBy.setDBColumnName("INVESTIGATED_BY");
        addColumn(colInvestigationBy);
        
        //Setting properties for Status
        Column colStatus =new Column("Status");
        colStatus.setMethodName("getStatusTypeID");
        colStatus.setColumnWidth("8%");
        colStatus.setIsHeaderLink(false);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("DESCRIPTION");
        addColumn(colStatus);

    }//end of setTableProperties()

}//end of Investigation InvestigationHistory