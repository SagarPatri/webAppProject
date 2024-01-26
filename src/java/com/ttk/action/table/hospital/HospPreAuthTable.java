/** as per Hospital Login
 * @ (#) HospPreAuthTable 5, 2006
 * Project      : TTK HealthCare Services
 * File         : PreAuthTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : May 5, 2006
 *
 * @author       :  Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.hospital;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class HospPreAuthTable extends Table
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

      /*  //ADD IMAGE THINGS OVER HERE
        //Setting properties for image Priority
        Column colImage1 = new Column("!");
        colImage1.setMethodName("getPriorityTypeID");
        colImage1.setIsHeaderLink(true);
        colImage1.setImageName("getPriorityImageName");
        colImage1.setImageTitle("getPriorityImageTitle");
        colImage1.setHeaderLinkTitle("Sort by:Priority");
        colImage1.setDBColumnName("PAT_PRIORITY_GENERAL_TYPE_ID");
        addColumn(colImage1);*/
        
      /* //Setting properties for image Priority
        Column colImage4 = new Column("");
        colImage4.setIsImage(true);
        colImage4.setIsHeaderLink(true);
        colImage4.setImageName("getRejImageName");
        colImage4.setImageTitle("getRejImageTitle");
        colImage4.setDBColumnName("REJECT_COMPLETE_YN");
        addColumn(colImage4);*/

        //Setting properties for Cashless No.
        Column colPreAuthNo=new Column("Cashless No.");
        colPreAuthNo.setMethodName("getPreAuthNo");
        colPreAuthNo.setColumnWidth("25%");
        colPreAuthNo.setIsLink(true);
        colPreAuthNo.setIsHeaderLink(true);
        colPreAuthNo.setImageName("getShortfallImageName");
        colPreAuthNo.setImageTitle("getShortfallImageTitle");
        colPreAuthNo.setHeaderLinkTitle("Sort by: Cashless No.");
        colPreAuthNo.setDBColumnName("PRE_AUTH_NUMBER");
        addColumn(colPreAuthNo);
/*
        //Setting properties for Hospital Name.
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
        colClaimantName.setColumnWidth("25%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("CLAIMANT_NAME");
        addColumn(colClaimantName);
       
        /*//Setting properties for  Assigned To.
        Column colAssignedTo=new Column("Assigned To");
        colAssignedTo.setMethodName("getAssignedTo");
        colAssignedTo.setColumnWidth("15%");
        colAssignedTo.setIsHeaderLink(true);
        colAssignedTo.setHeaderLinkTitle("Sort by: Assigned To");
        colAssignedTo.setDBColumnName("CONTACT_NAME");
        addColumn(colAssignedTo);*/

        //Setting properties for  Received Date.
        Column colReceivedDate=new Column("Received Date");
        colReceivedDate.setMethodName("getPreAuthReceivedDate");
        colReceivedDate.setColumnWidth("15%");
        colReceivedDate.setIsHeaderLink(true);
        colReceivedDate.setHeaderLinkTitle("Sort by: Received Date");
        colReceivedDate.setDBColumnName("PAT_RECEIVED_DATE");
        addColumn(colReceivedDate);

        
        Column colPreAuthStatus=new Column("Status");
        colPreAuthStatus.setMethodName("getStatusTypeID");
        colPreAuthStatus.setColumnWidth("10%");
        colPreAuthStatus.setIsHeaderLink(true);
        colPreAuthStatus.setHeaderLinkTitle("Sort by: Status");
        colPreAuthStatus.setDBColumnName("PAT_STATUS");
        addColumn(colPreAuthStatus);
       /* //Setting properties for image Preauth
        Column colImage2 = new Column("");
        colImage2.setIsImage(true);
        colImage2.setIsImageLink(true);
        colImage2.setImageName("getPreAuthImageName");
        colImage2.setImageTitle("getPreAuthImageTitle");
        addColumn(colImage2);

        //Setting properties for image Assign
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
    }// end of public void setTableProperties()

}// end of PreAuthTable class
