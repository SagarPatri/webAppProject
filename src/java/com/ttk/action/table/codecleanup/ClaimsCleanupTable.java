package com.ttk.action.table.codecleanup;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ClaimsCleanupTable extends Table
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

        //Setting properties for Claim No.
        Column colClaimNo=new Column("Claim No.");
        colClaimNo.setMethodName("getPreAuthNo");
        colClaimNo.setColumnWidth("18%");
        colClaimNo.setIsLink(true);
        colClaimNo.setIsHeaderLink(true);
        colClaimNo.setImageName("");
        colClaimNo.setImageTitle("");
        colClaimNo.setHeaderLinkTitle("Sort by: Claim No.");
        colClaimNo.setDBColumnName("CLAIM_NUMBER");
        addColumn(colClaimNo);

        //Setting properties for Hospital Name.
        Column colHospitalName=new Column("Hospital Name");
        colHospitalName.setMethodName("getHospitalName");
        colHospitalName.setColumnWidth("15%");
        colHospitalName.setIsHeaderLink(true);
        colHospitalName.setHeaderLinkTitle("Sort by: Hospital Name");
        colHospitalName.setDBColumnName("HOSP_NAME");
        addColumn(colHospitalName);

        //Setting properties for  Enrollment Id.
        Column colEnrollmentId=new Column("Enrollment Id");
        colEnrollmentId.setMethodName("getEnrollmentID");
        colEnrollmentId.setColumnWidth("23%");
        colEnrollmentId.setIsHeaderLink(true);
        colEnrollmentId.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentId);

        //Setting properties for  Member Name.
        Column colClaimantName=new Column("Member Name");
        colClaimantName.setMethodName("getClaimantName");
        colClaimantName.setColumnWidth("16%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("CLAIMANT_NAME");
        addColumn(colClaimantName);

        //Setting properties for  Adm. Date.
        Column colAdmDate=new Column("Adm. Date");
        colAdmDate.setMethodName("getClaimAdmissionDate");
        colAdmDate.setColumnWidth("10%");
        colAdmDate.setIsHeaderLink(true);
        colAdmDate.setHeaderLinkTitle("Sort by: Adm. Date");
        colAdmDate.setDBColumnName("DATE_OF_ADMISSION");
        addColumn(colAdmDate);

        //Setting properties for Aythorization No.
        Column colAuthorizationNo=new Column("Authorization No.");
        colAuthorizationNo.setMethodName("getAuthNbr");
        colAuthorizationNo.setColumnWidth("15%");
        colAuthorizationNo.setIsHeaderLink(true);
        colAuthorizationNo.setHeaderLinkTitle("Sort by: Authorization No.");
        colAuthorizationNo.setDBColumnName("AUTH_NUMBER");
        addColumn(colAuthorizationNo);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    } //end of setTableProperties()
}//end of ClaimsCleanupTable class
