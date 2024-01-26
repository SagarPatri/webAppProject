package com.ttk.action.table.onlineforms.insuranceLogin;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class MemberDataTable extends Table{

	
	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	
	@Override
	public void setTableProperties() {

		setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(20);

        //Setting properties for ENROLLMENT ID
        Column colEnrollmentId = new Column("Enrolment ID");
        colEnrollmentId.setMethodName("getEnrollmentId");
        colEnrollmentId.setColumnWidth("40%");
        colEnrollmentId.setIsLink(true);
        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentId);

        //Setting properties for Member Name
        Column colEmployeeName = new Column("Employee Name");
        colEmployeeName.setMethodName("getEmployeeName");
        colEmployeeName.setColumnWidth("15%");
        colEmployeeName.setDBColumnName("EMP_NAME");
        addColumn(colEmployeeName);
        
      //Setting properties for Member Name
        Column colMemberName = new Column("Beneficiary name");
        colMemberName.setMethodName("getMemberName");
        colMemberName.setColumnWidth("30%");
        colMemberName.setDBColumnName("MEM_NAME");
        addColumn(colMemberName);
        
      //Setting properties for Member Name
        Column colEmployeeNo = new Column("Employee No");
        colEmployeeNo.setMethodName("getEmployeeNo");
        colEmployeeName.setColumnWidth("15%");
        colEmployeeNo.setDBColumnName("EMPLOYEE_NOE");
        addColumn(colEmployeeNo);
	}

}
