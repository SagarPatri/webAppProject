package com.ttk.action.table.codecleanup;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class PreauthCleanupTable extends Table
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
        
        //ADD IMAGE THINGS OVER HERE
        //Setting properties for image Priority
        Column colImage1 = new Column("!");
        colImage1.setMethodName("getPriorityTypeID");
        colImage1.setIsHeaderLink(true);
        colImage1.setImageName("getPriorityImageName");
        colImage1.setImageTitle("getPriorityImageTitle");
        colImage1.setHeaderLinkTitle("Sort by:Priority");
        colImage1.setDBColumnName("PAT_PRIORITY_GENERAL_TYPE_ID");
        addColumn(colImage1);

        //Setting properties for Cashless No.
        Column colPreAuthNo=new Column("Cashless No.");
        colPreAuthNo.setMethodName("getPreAuthNo");
        colPreAuthNo.setColumnWidth("21%");
        colPreAuthNo.setIsLink(true);
        colPreAuthNo.setIsHeaderLink(true);
        colPreAuthNo.setImageName("getAssignedImageName");
        colPreAuthNo.setImageTitle("getAssignedImageTitle");
        colPreAuthNo.setHeaderLinkTitle("Sort by: Cashless No.");
        colPreAuthNo.setDBColumnName("PRE_AUTH_NUMBER");
        addColumn(colPreAuthNo);

        //Setting properties for Hospital Name.
        Column colHospitalName=new Column("Hospital Name");
        colHospitalName.setMethodName("getHospitalName");
        colHospitalName.setColumnWidth("20%");
        colHospitalName.setIsHeaderLink(true);
        colHospitalName.setHeaderLinkTitle("Sort by: Hospital Name");
        colHospitalName.setDBColumnName("HOSP_NAME");
        addColumn(colHospitalName);

        //Setting properties for  Enrollment Id.
        Column colEnrollmentId=new Column("Enrollment Id");
        colEnrollmentId.setMethodName("getEnrollmentID");
        colEnrollmentId.setColumnWidth("26%");
        colEnrollmentId.setIsHeaderLink(true);
        colEnrollmentId.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentId);

        //Setting properties for  Member Name.
        Column colClaimantName=new Column("Member Name");
        colClaimantName.setMethodName("getClaimantName");
        colClaimantName.setColumnWidth("20%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("CLAIMANT_NAME");
        addColumn(colClaimantName);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }// end of public void setTableProperties()

}//end of PreauthCleanupTable()
