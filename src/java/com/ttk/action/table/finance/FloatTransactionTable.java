/**
 * @ (#) FloatTransactionTable.java June 10th, 2006
 * Project      : TTK HealthCare Services
 * File         : FloatTransactionTable.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : June 10th, 2006
 *
 * @author       :  Krupa J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * @author krupa_j
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FloatTransactionTable extends Table
{

    private static final long serialVersionUID = 1L;

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Transaction No
        Column colTransactionNo = new Column("Transaction No.");
        colTransactionNo.setMethodName("getTransNbr");
        colTransactionNo.setColumnWidth("25%");
        colTransactionNo.setImageName("getImageName");
        colTransactionNo.setImageTitle("getImageTitle");
        colTransactionNo.setIsHeaderLink(true);
        colTransactionNo.setHeaderLinkTitle("Sort by: Transaction No.");
        colTransactionNo.setIsLink(true);
        colTransactionNo.setLinkTitle("Edit Transaction No");
        colTransactionNo.setDBColumnName("FLOAT_TRN_NUMBER");
        addColumn(colTransactionNo);

        //Setting properties for Type
        Column colType = new Column("Type");
        colType.setMethodName("getTransTypeDesc");
        colType.setColumnWidth("25%");
        colType.setIsHeaderLink(true);
        colType.setHeaderLinkTitle("Sort by: Type");
        colType.setDBColumnName("FLT_TRN_TYPE");
        addColumn(colType);

        //Setting properties for Date
        Column colDate = new Column("Date");
        colDate.setMethodName("getTransactionDate");
        colDate.setColumnWidth("25%");
        colDate.setIsHeaderLink(true);
        colDate.setHeaderLinkTitle("Sort by: Date");
        colDate.setDBColumnName("FLT_TRN_DATE");
        addColumn(colDate);

        //Setting properties for Amount (Rs)
        Column colAge = new Column("Amount");
        colAge.setMethodName("getTransAmt");
        colAge.setColumnWidth("25%");
        colAge.setIsHeaderLink(true);
        colAge.setHeaderLinkTitle("Sort by: Amount (Rs)");
        colAge.setDBColumnName("FLT_TRN_AMOUNT");
        addColumn(colAge);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }//end of setTableProperties()

}//end of FloatTransaction class
