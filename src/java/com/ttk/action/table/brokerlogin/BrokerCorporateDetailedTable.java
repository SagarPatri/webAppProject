/**
 *   @ (#) BrokerCorporateTable.java Jan 05, 2015
 *   Project 	   : Dubai Project
 *   File          : BrokerCorporateTable.java
 *   Author        : Nagababu K
 *   Company       : RCS
 *   Date Created  : Jan 05, 2015
 *
 *   @author       :  Nagababu K
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 *
 */
package com.ttk.action.table.brokerlogin;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class BrokerCorporateDetailedTable extends Table
{
    /**
	 * 
	 */
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
        
        //Setting properties for Cashless No.
        Column colEnrolID=new Column("Enrolment ID");
        colEnrolID.setMethodName("getEnrolmentID");
        colEnrolID.setColumnWidth("25%");
        colEnrolID.setIsLink(true);
        colEnrolID.setIsHeaderLink(true);
        colEnrolID.setHeaderLinkTitle("Sort by:Enrolment ID");
        colEnrolID.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrolID);
        
        Column colEmployeeName=new Column("Employee Name");
        colEmployeeName.setMethodName("getEmployeeName");
        colEmployeeName.setColumnWidth("25%");
        colEmployeeName.setIsHeaderLink(true);
        colEmployeeName.setHeaderLinkTitle("Sort by:Employee Name");
        colEmployeeName.setDBColumnName("EMP_NAME");
        addColumn(colEmployeeName);
        
        Column colMemberName=new Column("Beneficiary Name");
        colMemberName.setMethodName("getMemberName");
        colMemberName.setColumnWidth("25%");
        colMemberName.setIsHeaderLink(true);
        colMemberName.setHeaderLinkTitle("Sort by:Beneficiary Name");
        colMemberName.setDBColumnName("MEM_NAME");
        addColumn(colMemberName);
        
        Column colEmployeeNO=new Column("Employee NO");
        colEmployeeNO.setMethodName("getEmployeeNO");
        colEmployeeNO.setColumnWidth("25%");
        colEmployeeNO.setIsHeaderLink(true);
        colEmployeeNO.setHeaderLinkTitle("Sort by:Employee NO");
        colEmployeeNO.setDBColumnName("EMPLOYEE_NO");
        addColumn(colEmployeeNO);
         
/*
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);*/
    }// end of public void setTableProperties()

}// end of BrokerCorporateDetailedTable class
