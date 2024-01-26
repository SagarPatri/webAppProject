package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class DebitNoteTable extends Table
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

        //Setting properties for Debit No.
        Column colDebitNoteNbr = new Column("Debit Note");
        colDebitNoteNbr.setMethodName("getDebitNoteNbr");
        colDebitNoteNbr.setColumnWidth("25%");
        colDebitNoteNbr.setIsHeaderLink(true);
        colDebitNoteNbr.setHeaderLinkTitle("Sort by: Debit Note ");
        colDebitNoteNbr.setIsLink(true);
        colDebitNoteNbr.setLinkTitle("Account No.");
        colDebitNoteNbr.setDBColumnName("DEBIT_NOTE_NUMBER");
        addColumn(colDebitNoteNbr);

        //Setting properties for Debit Date
        Column colDebitNoteDate = new Column("Debit Date");
        colDebitNoteDate.setMethodName("getDbNoteDate");
        colDebitNoteDate.setColumnWidth("25%");
        colDebitNoteDate.setIsHeaderLink(true);
        colDebitNoteDate.setHeaderLinkTitle("Sort by: Debit Date");
        colDebitNoteDate.setDBColumnName("DEBIT_DATE");
        addColumn(colDebitNoteDate);

        
  //Setting properties for Final Date
        Column colFinalDate = new Column("Final Date");
        colFinalDate.setMethodName("getDbFinalDate");
        colFinalDate.setColumnWidth("25%");
        colFinalDate.setIsHeaderLink(true);
        colFinalDate.setHeaderLinkTitle("Sort by: Final Date");
        colFinalDate.setDBColumnName("FINAL_DATE");
        addColumn(colFinalDate);

        //Setting properties for Debit Typr
        Column colDebitNoteTypeID  =new Column("Debit Type");
        colDebitNoteTypeID.setMethodName("getDebitNoteDesc");
        colDebitNoteTypeID.setColumnWidth("25%");
        colDebitNoteTypeID.setIsHeaderLink(true);
        colDebitNoteTypeID.setHeaderLinkTitle("Sort by: Debit Type");
        colDebitNoteTypeID.setDBColumnName("DESCRIPTION");
        addColumn(colDebitNoteTypeID);

//      Setting properties for Debit Amount
       Column colDebitAmt  =new Column("Debit Note Amt. (OMR)");
        colDebitAmt.setMethodName("getDebitNoteAmt");
        colDebitAmt.setColumnWidth("25%");
        colDebitAmt.setIsHeaderLink(true);
        colDebitAmt.setHeaderLinkTitle("Sort by: Debit Note Amt. (AED)");
        colDebitAmt.setDBColumnName("DEBIT_AMOUNT");
        addColumn(colDebitAmt);
        
        /*Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);*/
    }//end of setTableProperties()
}//end of DebitNoteTable()
