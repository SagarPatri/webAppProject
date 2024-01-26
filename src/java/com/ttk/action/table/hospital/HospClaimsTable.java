/**
Hospital Login
* @ (#) HospClaimsTable Jul 14, 2006
* Project       : TTK HealthCare Services
* File          : ClaimsTable.java
* Author        : Chandrasekaran J
* Company       : Span Systems Corporation
* Date Created  : Jul 14, 2006

* @author       : Chandrasekaran J
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.table.hospital;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class HospClaimsTable extends Table
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
        colClaimNo.setColumnWidth("20%");
        colClaimNo.setIsLink(true);
        colClaimNo.setIsHeaderLink(true);
        colClaimNo.setImageName("");
        colClaimNo.setImageTitle("");
        colClaimNo.setHeaderLinkTitle("Sort by: Claim No.");
        colClaimNo.setDBColumnName("CLAIM_NUMBER");
        addColumn(colClaimNo);

    /*    //Setting properties for Hospital Name.
        Column colHospitalName=new Column("Hospital Name");
        colHospitalName.setMethodName("getHospitalName");
        colHospitalName.setColumnWidth("15%");
        colHospitalName.setIsHeaderLink(true);
        colHospitalName.setHeaderLinkTitle("Sort by: Hospital Name");
        colHospitalName.setDBColumnName("HOSP_NAME");
        addColumn(colHospitalName);
*/
        //Setting properties for  Enrollment Id.
        Column colEnrollmentId=new Column("Enrollment Id");
        colEnrollmentId.setMethodName("getEnrollmentID");
        colEnrollmentId.setColumnWidth("25%");
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

       /* //Setting properties for  Assigned To.
        Column colAssignedTo=new Column("Assigned To");
        colAssignedTo.setMethodName("getAssignedTo");
        colAssignedTo.setColumnWidth("15%");
        colAssignedTo.setIsHeaderLink(true);
        colAssignedTo.setHeaderLinkTitle("Sort by: Assigned To");
        colAssignedTo.setDBColumnName("CONTACT_NAME");
        addColumn(colAssignedTo);*/

        //Setting properties for  Adm. Date.
        Column colAdmDate=new Column("Adm. Date");
        colAdmDate.setMethodName("getClaimAdmissionDate");
        colAdmDate.setColumnWidth("10%");
        colAdmDate.setIsHeaderLink(true);
        colAdmDate.setHeaderLinkTitle("Sort by: Adm. Date");
        colAdmDate.setDBColumnName("DATE_OF_ADMISSION");
        addColumn(colAdmDate);
        
      //Setting properties for  Discharge. Date.New Req - Kishor
       Column colDisDate=new Column("Dis. Date");
       colDisDate.setMethodName("getDischargeDate1");
       colDisDate.setColumnWidth("10%");
       colDisDate.setIsHeaderLink(true);
       colDisDate.setHeaderLinkTitle("Sort by: Dis. Date");
       colDisDate.setDBColumnName("DATE_OF_DISCHARGE");
        addColumn(colDisDate);

        //Setting properties for Aythorization No.
        Column colAuthorizationNo=new Column("Authorization No.");
        colAuthorizationNo.setMethodName("getAuthNbr");
        colAuthorizationNo.setColumnWidth("15%");
        colAuthorizationNo.setIsHeaderLink(true);
        colAuthorizationNo.setHeaderLinkTitle("Sort by: Authorization No.");
        colAuthorizationNo.setDBColumnName("AUTH_NUMBER");
        addColumn(colAuthorizationNo);

        Column colPreAuthStatus=new Column("Status");
        colPreAuthStatus.setMethodName("getStatusTypeID");
        colPreAuthStatus.setColumnWidth("10%");
        colPreAuthStatus.setIsHeaderLink(true);
        colPreAuthStatus.setHeaderLinkTitle("Sort by: Status");
        colPreAuthStatus.setDBColumnName("CLM_STATUS");
        addColumn(colPreAuthStatus);
        
        
      /*  //Setting properties for image Assighn
        Column colImage3 = new Column("");
        colImage3.setIsImage(true);
        colImage3.setIsImageLink(true);
        colImage3.setImageName("getAssignImageName");
        colImage3.setImageTitle("getAssignImageTitle");
        colImage3.setVisibility(false);
        addColumn(colImage3);*/

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    } //end of setTableProperties()
}// end of ClaimsTable class


