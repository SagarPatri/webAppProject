/**

* @ (#) CodingClaimsTable.java Aug 29, 2007
* Project       : TTK HealthCare Services
* File          : CodingClaimsTable.java
* Author        : Chandrasekaran J
* Company       : Span Systems Corporation
* Date Created  : Aug 29, 2007

* @author       : Chandrasekaran J
* Modified by   :
* Modified date :
* Reason :
*/
package com.ttk.action.table.coding;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class CodingClaimsTable extends Table
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
        colClaimNo.setColumnWidth("14%");
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
        colHospitalName.setColumnWidth("14%");
        colHospitalName.setIsHeaderLink(true);
        colHospitalName.setHeaderLinkTitle("Sort by: Hospital Name");
        colHospitalName.setDBColumnName("HOSP_NAME");
        addColumn(colHospitalName);

        //Setting properties for  Enrollment Id.
        Column colEnrollmentId=new Column("Enrollment Id");
        colEnrollmentId.setMethodName("getEnrollmentID");
        colEnrollmentId.setColumnWidth("13%");
        colEnrollmentId.setIsHeaderLink(true);
        colEnrollmentId.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentId);

        //Setting properties for  Member Name.
        Column colClaimantName=new Column("Member Name");
        colClaimantName.setMethodName("getClaimantName");
        colClaimantName.setColumnWidth("10%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("CLAIMANT_NAME");
        addColumn(colClaimantName);

        //Setting properties for  Assigned To.
        Column colAssignedTo=new Column("Assigned To");
        colAssignedTo.setMethodName("getAssignedTo");
        colAssignedTo.setColumnWidth("10%");
        colAssignedTo.setIsHeaderLink(true);
        colAssignedTo.setHeaderLinkTitle("Sort by: Assigned To");
        colAssignedTo.setDBColumnName("CONTACT_NAME");
        addColumn(colAssignedTo);

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
        colAuthorizationNo.setColumnWidth("14%");
        colAuthorizationNo.setIsHeaderLink(true);
        colAuthorizationNo.setHeaderLinkTitle("Sort by: Authorization No.");
        colAuthorizationNo.setDBColumnName("AUTH_NUMBER");
        addColumn(colAuthorizationNo);
		
		//added for Decoupling
        Column colDataEntryStatus = new Column("Status");
        colDataEntryStatus.setMethodName("getStatus");
        colDataEntryStatus.setColumnWidth("10%");
        colDataEntryStatus.setIsHeaderLink(true);
        colDataEntryStatus.setHeaderLinkTitle("Sort by: Status");
        colDataEntryStatus.setDBColumnName("Status");
        addColumn(colDataEntryStatus);        
        //ended

        //Setting properties for image Assighn
        Column colImage3 = new Column("");
        colImage3.setIsImage(true);
        colImage3.setIsImageLink(true);
        colImage3.setImageName("getAssignImageName");
        colImage3.setImageTitle("getAssignImageTitle");
        colImage3.setVisibility(true);
        addColumn(colImage3);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    } //end of setTableProperties()
	
}//end of class CodingClaimsTable()
