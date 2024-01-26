package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class AccountsManagerTable extends Table{
	
	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    // The order of the column should remain as same as here, if any new colum needs to be added then add at the end
    public void setTableProperties()
    {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(20);
        
        //Setting properties for User Id
        Column colUserID=new Column("User ID");
        colUserID.setMethodName("getUserID");
        colUserID.setColumnWidth("15%");
        colUserID.setIsLink(true);
        colUserID.setIsHeaderLink(true);
        colUserID.setHeaderLinkTitle("Sort by: User ID");
        colUserID.setDBColumnName("USER_ID");
        addColumn(colUserID);

        //Setting properties for Name
        Column colName=new Column("Name");
        colName.setMethodName("getUserName");
        colName.setColumnWidth("20%");
        colName.setIsHeaderLink(true);
        colName.setHeaderLinkTitle("Sort by: Name");
        colName.setDBColumnName("CONTACT_NAME");
        addColumn(colName);

        //Setting properties for Role
        Column colRole=new Column("Role");
        colRole.setMethodName("getRoleName");
        colRole.setColumnWidth("15%");
        colRole.setIsHeaderLink(true);
        colRole.setHeaderLinkTitle("Sort by:Role");
        colRole.setDBColumnName("ROLE_NAME");
        addColumn(colRole);
        
        //	Setting properties forVidal Health TPA Branch
        Column colEmpNo =new Column("Employee No.");
        colEmpNo.setMethodName("getEmployeeNbr");
        colEmpNo.setColumnWidth("20%");
        colEmpNo.setIsHeaderLink(true);
        colEmpNo.setHeaderLinkTitle("Sort by: Employee No.");
        colEmpNo.setDBColumnName("EMPLOYEE_NUMBER");
        addColumn(colEmpNo);
        //Setting properties forVidal Health TPABranch
        Column colTTKBranch =new Column("Vidal Health Branch");
        colTTKBranch.setMethodName("getName");
        colTTKBranch.setColumnWidth("50%");
        colTTKBranch.setIsHeaderLink(true);
        colTTKBranch.setHeaderLinkTitle("Sort by: Vidal Health Branch");
        colTTKBranch.setDBColumnName("OFFICE_NAME");
        addColumn(colTTKBranch);

     }//end of public void setTableProperties()

}//end of AssociateUsersTable


