/**
 * @ (#) BankListTable.java June 13, 2006
 * Project      : TTK HealthCare Services
 * File         :BankListTable.java
 * Author       : Arun K M
 * Company      : Span Systems Corporation
 * Date Created : June 13, 2006
 *
 * @author       :  Arun K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Bank details
 *
 */
public class BankListTable extends Table{

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Bank Name
        Column colBankName = new Column("Bank Name");
        colBankName.setMethodName("getBankName");
        colBankName.setColumnWidth("60%");
        colBankName.setIsHeaderLink(true);
        colBankName.setHeaderLinkTitle("Sort by: Bank Name");
        colBankName.setIsLink(true);
        colBankName.setLinkTitle("Edit Bank");
        colBankName.setDBColumnName("BANK_NAME");
        addColumn(colBankName);

        //Setting properties for City
        Column colCity =new Column("City");
        colCity.setMethodName("getCityDesc");
        colCity.setColumnWidth("40%");
        colCity.setIsHeaderLink(true);
        colCity.setHeaderLinkTitle("Sort by: City");
        colCity.setDBColumnName("CITY");
        addColumn(colCity);

    }//end of setTableProperties()

}//end of BankListTable
