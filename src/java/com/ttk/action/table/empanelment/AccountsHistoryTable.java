/**
 * @ (#) AccountsHistoryTable.javaNov 26, 2005
 * Project      : TTK HealthCare Services
 * File         : AccountsHistoryTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Nov 26, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class AccountsHistoryTable extends Table // implements TableObjectInterface,Serializable
{
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(20);
        
        // Setting the propeties for Ammount
        Column colAmmount = new Column("Amount(Rs)");
        colAmmount.setMethodName("getAmount");
        colAmmount.setColumnWidth("25%");
        colAmmount.setIsLink(true);
        colAmmount.setIsHeaderLink(true);
        colAmmount.setHeaderLinkTitle("Sort by: Ammount(Rs) ");
        colAmmount.setDBColumnName("AMOUNT");
        addColumn(colAmmount);
        
        // Setting the propeties for Bank Name
        Column colName = new Column("Bank Name");
        colName.setMethodName("getGuaranteeBankName");
        colName.setColumnWidth("25%");
        colName.setIsHeaderLink(true);
        colName.setHeaderLinkTitle("Sort by: Bank Name ");
        colName.setDBColumnName("BANK_NAME");
        addColumn(colName);
        
        // Setting the properties for Employee Start Date
        Column colStartDate = new Column("Emp.Start Date ");
        colStartDate.setMethodName("getFormattedStartDate");
        colStartDate.setColumnWidth("25%");
        colStartDate.setIsHeaderLink(true);
        colStartDate.setHeaderLinkTitle("Sort by:Emp.Start Date");
        colStartDate.setDBColumnName("FROM_DATE");
        addColumn(colStartDate);
        
        //Setting properties for Employee End date
        Column colToDate = new Column("Emp.End Date ");
        colToDate.setMethodName("getFormattedEndDate");
        colToDate.setColumnWidth("25%");
        colToDate.setIsHeaderLink(true);
        colToDate.setHeaderLinkTitle("Sort by: Emp.End Date");
        colToDate.setDBColumnName("TO_DATE");
        addColumn(colToDate);
    }// end of setTableProperties()

}// end of class AccountsHistoryTable
