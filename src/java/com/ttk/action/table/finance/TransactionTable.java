/**
 * @ (#) TransactionTable.java June 09, 2006
 * Project      : TTK HealthCare Services
 * File         : TransactionTable.java
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : June 09, 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * this class provides the information of Transaction Table
 */
public class TransactionTable extends Table
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

        //Setting properties for Transaction No.
        Column colTransactionNo = new Column("Transaction No.");
        colTransactionNo.setMethodName("getTransNbr");
        colTransactionNo.setColumnWidth("25%");
        colTransactionNo.setIsLink(true);
        colTransactionNo.setIsHeaderLink(true);
        colTransactionNo.setImageName("getImageName");
        colTransactionNo.setImageTitle("getImageTitle");
        colTransactionNo.setLinkTitle("Edit Transaction");
        colTransactionNo.setHeaderLinkTitle("Sort by: Transaction No.");
        colTransactionNo.setDBColumnName("BANK_TRN_NUMBER");
        addColumn(colTransactionNo);

        //Setting properties for Type
        Column colType = new Column("Type");
        colType.setMethodName("getTransTypeDesc");
        colType.setColumnWidth("25%");
        colType.setIsHeaderLink(true);
        colType.setHeaderLinkTitle("Sort by: Type");
        colType.setDBColumnName("type");
        addColumn(colType);

        //Setting properties for Date
        Column colDate = new Column("Date");
        colDate.setMethodName("getBankTransDate");
        colDate.setColumnWidth("25%");
        colDate.setIsHeaderLink(true);
        colDate.setHeaderLinkTitle("Sort by: Date");
        colDate.setDBColumnName("TRN_DATE");
        addColumn(colDate);

        //Setting properties for Amount (Rs)
        Column colAmount = new Column("Amount");
        colAmount.setMethodName("getTransAmt");
        colAmount.setColumnWidth("25%");
        colAmount.setIsHeaderLink(true);
        colAmount.setHeaderLinkTitle("Sort by: Amount");
        colAmount.setDBColumnName("TRN_AMOUNT");
        addColumn(colAmount);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }// end of public void setTableProperties()
}// end of TransactionTable Class