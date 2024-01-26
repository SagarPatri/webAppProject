
/**
 * @ (#) UploadMOUDocsAction.java 31 Dec 2014
 * Project      : TTK HealthCare Services
 * File         :UploadMOUDocsAction.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 31 Dec 2014
 *
 * @author       : Kishor kumar S H
 * Modified by   : 
 * Modified date : 31 Dec 2014
 * Reason        : 
 */

package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Bank details
 * 
 */
public class AccountsSearchTable extends Table{
    
    /**
     * This creates the column properties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(20);
        
        //Setting properties for BANK_NAME
        Column colProductName = new Column("Bank Name");
        colProductName.setMethodName("getBankNameAdd");
        colProductName.setColumnWidth("33%");
        colProductName.setIsHeaderLink(true);
        colProductName.setHeaderLinkTitle("Sort by: Bank Name");
        colProductName.setIsLink(true);
        colProductName.setLinkTitle("Edit Bank");
        colProductName.setDBColumnName("BANK_NAME");
        addColumn(colProductName);
        
        //Setting properties for ACCOUNT_NUMBER
        Column colInsCompanyName = new Column("Account Number");
        colInsCompanyName.setMethodName("getAccountNumberAdd");
        colInsCompanyName.setColumnWidth("33%");
        colInsCompanyName.setIsHeaderLink(true);
        colInsCompanyName.setHeaderLinkTitle("Sort by: Account Number");
        colInsCompanyName.setDBColumnName("ACCOUNT_NUMBER");
        addColumn(colInsCompanyName);
        
        
        //Setting properties for BRANCH_NAME
        Column colTtkBranch =new Column("Branch Name");
        colTtkBranch.setMethodName("getBranchNameAdd");
        colTtkBranch.setColumnWidth("33%");
        colTtkBranch.setIsHeaderLink(true);
        colTtkBranch.setHeaderLinkTitle("Sort by: Branch name");
        colTtkBranch.setDBColumnName("BRANCH_NAME");    
        addColumn(colTtkBranch);
        
        
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");       
        addColumn(colSelect); 
    }//end of setTableProperties()

}//end of AccountsSearchTable
