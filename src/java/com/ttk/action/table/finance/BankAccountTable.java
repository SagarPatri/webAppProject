/**
 * @ (#) BankAccountTable.java June 10, 2006
 * Project       : TTK HealthCare Services
 * File          : BankAccountTable.java
 * Author        : Harsha Vardhan B N
 * Company       : Span Systems Corporation
 * Date Created  : June 10, 2006
 *
 * @author       :
 * Modified by   : Harsha Vardhan B N
 * Modified date : June 13, 2006
 * Reason        :
 */

package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class BankAccountTable extends Table {
    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */

        public void setTableProperties()
        {
            setRowCount(10);
            setCurrentPage(1);
            setPageLinkCount(10);

            //Setting properties for Account No
            Column colAccountNo = new Column("Account No.");
            colAccountNo.setMethodName("getAccountNO");
            colAccountNo.setColumnWidth("20%");
            colAccountNo.setIsLink(true);
            colAccountNo.setLinkTitle("Edit Account No");
            colAccountNo.setIsHeaderLink(true);
            colAccountNo.setHeaderLinkTitle("Sort by Account No.");
            colAccountNo.setDBColumnName("ACCOUNT_NUMBER");
            addColumn(colAccountNo);

            //Setting properties for Account Name
            Column colAccountName = new Column("Account Name");
            colAccountName.setMethodName("getAccountName");
            colAccountName.setColumnWidth("20%");
            colAccountName.setIsHeaderLink(true);
            colAccountName.setHeaderLinkTitle("Sort by Account Name");
            colAccountName.setDBColumnName("ACCOUNT_NAME");
            addColumn(colAccountName);

            //Setting properties for Bank Name
            Column colBankName = new Column("Bank Name");
            colBankName.setMethodName("getBankName");
            colBankName.setColumnWidth("20%");
            colBankName.setIsHeaderLink(true);
            colBankName.setHeaderLinkTitle("Sort by Bank Name");
            colBankName.setDBColumnName("BANK_NAME");
            addColumn(colBankName);

//			Setting properties for Office Type
            Column colOfficeType = new Column("Office Type");
            colOfficeType.setMethodName("getOfficeTypeDesc");
            colOfficeType.setColumnWidth("15%");
            colOfficeType.setIsHeaderLink(true);
            colOfficeType.setHeaderLinkTitle("Sort by Office Type");
            colOfficeType.setDBColumnName("OFFICE_TYPE");
            addColumn(colOfficeType);

            //Setting properties forVidal Healthcare Branch
            Column colTTKBranch = new Column("Branch Location");
            colTTKBranch.setMethodName("getTtkBranch");
            colTTKBranch.setColumnWidth(" 15%");
            colTTKBranch.setIsHeaderLink(true);
            colTTKBranch.setHeaderLinkTitle("Sort by Vidal Health Branch");
            colTTKBranch.setDBColumnName("OFFICE_NAME");
            addColumn(colTTKBranch);

            //Setting properties for Status
            Column colStatus = new Column("Status");
            colStatus.setMethodName("getStatusDesc");
            colStatus.setColumnWidth("10%");
            colStatus.setIsHeaderLink(true);
            colStatus.setHeaderLinkTitle("Sort by Status");
            colStatus.setDBColumnName("STATUS");
            addColumn(colStatus);


            //Setting properties for check box
            Column colSelect = new Column("Select");
            colSelect.setComponentType("checkbox");
            colSelect.setComponentName("chkopt");
            addColumn(colSelect);
        }//end of public void setTableProperties()
    }//end of class BankAccountTable

