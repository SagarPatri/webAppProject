/**
 * @ (#) UserListTable .java Dec 28, 2005
 * Project       : TTK HealthCare Services
 * File          : UserListTable.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Dec 28, 2005
 * @author       : Bhaskar Sandra
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.usermanagement;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class UserListTable extends Table {

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    // The order of the column should remain as same as here, if any new colum needs to be added then add at the end
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        //Setting properties for User Id without link
        Column colUserId=new Column("User ID");
        colUserId.setMethodName("getUserID");
        colUserId.setColumnWidth("15%");
        colUserId.setIsHeaderLink(true);
        colUserId.setHeaderLinkTitle("Sort by: User ID");
        colUserId.setDBColumnName("USER_ID");
        addColumn(colUserId);
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
        //	Setting properties for Vidal Health TPA Branch
        Column colEmpNo =new Column("Employee No.");
        colEmpNo.setMethodName("getEmployeeNbr");
        colEmpNo.setColumnWidth("20%");
        colEmpNo.setIsHeaderLink(true);
        colEmpNo.setHeaderLinkTitle("Sort by: Employee No.");
        colEmpNo.setDBColumnName("EMPLOYEE_NUMBER");
        addColumn(colEmpNo);
        //Setting properties for Vidal Health TPA Branch
        Column colTTKBranch =new Column("Branch Location");
        colTTKBranch.setMethodName("getName");
        colTTKBranch.setColumnWidth("50%");
        colTTKBranch.setIsHeaderLink(true);
        colTTKBranch.setHeaderLinkTitle("Sort by: Vidal Health Branch");
        colTTKBranch.setDBColumnName("OFFICE_NAME");
        addColumn(colTTKBranch);

        //Setting properties for Hospital Name
        Column colHospName =new Column("Hospital Name");
        colHospName.setMethodName("getName");
        colHospName.setColumnWidth("22%");
        colHospName.setIsHeaderLink(true);
        colHospName.setHeaderLinkTitle("Sort by: Hospital Name");
        colHospName.setDBColumnName("HOSP_NAME");
        addColumn(colHospName);

        //Setting properties for City
        Column colCity =new Column("City");
        colCity.setMethodName("getCity");
        colCity.setColumnWidth("10%");
        colCity.setIsHeaderLink(true);
        colCity.setHeaderLinkTitle("Sort by: City");
        colCity.setDBColumnName("CITY");
        addColumn(colCity);

        //Setting properties for Empanelment No.
        Column colEmplNo =new Column("Empanelment No.");
        colEmplNo.setMethodName("getEmpanelmentNo");
        colEmplNo.setColumnWidth("18%");
        colEmplNo.setIsHeaderLink(true);
        colEmplNo.setHeaderLinkTitle("Sort by: Empanelment No.");
        colEmplNo.setDBColumnName("EMPANEL_NUMBER");
        addColumn(colEmplNo);

        //Setting properties for Insurance Company
        Column colInsCompany =new Column("Insurance Company");
        colInsCompany.setMethodName("getName");
        colInsCompany.setColumnWidth("25%");
        colInsCompany.setIsHeaderLink(true);
        colInsCompany.setHeaderLinkTitle("Sort by: Insurance Company");
        colInsCompany.setDBColumnName("INS_COMP_NAME");
        addColumn(colInsCompany);

        //Setting properties for Office Code
        Column colOfficeCode =new Column("Company Code");
        colOfficeCode.setMethodName("getOfficeCode");
        colOfficeCode.setColumnWidth("25%");
        colOfficeCode.setIsHeaderLink(true);
        colOfficeCode.setHeaderLinkTitle("Sort by: OfficeCode");
        colOfficeCode.setDBColumnName("INS_COMP_CODE_NUMBER");
        addColumn(colOfficeCode);

        //Setting properties for Group Name
        Column colGrpName=new Column("Group Name");
        colGrpName.setMethodName("getName");
        colGrpName.setColumnWidth("25%");
        colGrpName.setIsHeaderLink(true);
        colGrpName.setHeaderLinkTitle("Sort by:Group Name");
        colGrpName.setDBColumnName("GROUP_NAME");
        addColumn(colGrpName);

        //Setting properties for Group Id
        Column colGrpId=new Column("Group Id");
        colGrpId.setMethodName("getGroupID");
        colGrpId.setColumnWidth("25%");
        colGrpId.setIsHeaderLink(true);
        colGrpId.setHeaderLinkTitle("Sort by:Group Id");
        colGrpId.setDBColumnName("GROUP_ID");
        addColumn(colGrpId);
        
      //Setting properties for Broker Company - kocbroker
        Column colBroCompany =new Column("Broker Company");//13
        colBroCompany.setMethodName("getName");
        colBroCompany.setColumnWidth("25%");
        colBroCompany.setIsHeaderLink(true);
        colBroCompany.setHeaderLinkTitle("Sort by: Broker Company");
        colBroCompany.setDBColumnName("INS_COMP_NAME");
        addColumn(colBroCompany);
        
        
      //Setting properties for Broker Code
        Column colBrokerCode =new Column("Broker Code");//14
        colBrokerCode.setMethodName("getOfficeCode");
        colBrokerCode.setColumnWidth("25%");
        colBrokerCode.setIsHeaderLink(true);
        colBrokerCode.setHeaderLinkTitle("Sort by: OfficeCode");
        colBrokerCode.setDBColumnName("INS_COMP_CODE_NUMBER");
        addColumn(colBrokerCode);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }//end of public void setTableProperties()

}//end of AssociateUsersTable
