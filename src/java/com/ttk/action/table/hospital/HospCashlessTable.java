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


public class HospCashlessTable extends Table
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

     

        //Setting properties for Cashless No.
        Column colPreAuthNo=new Column("Reference No.");
        colPreAuthNo.setMethodName("getRefNo");
        colPreAuthNo.setColumnWidth("25%");
        colPreAuthNo.setIsLink(true);
        colPreAuthNo.setIsHeaderLink(true);
        colPreAuthNo.setImageName("getShortfallImageName");
        colPreAuthNo.setImageTitle("getShortfallImageTitle");
        colPreAuthNo.setHeaderLinkTitle("Sort by: Reference No.");
        colPreAuthNo.setDBColumnName("REF_NUMBER");
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
        Column colCashlessNo=new Column("Cashless No.");
        colCashlessNo.setMethodName("getPreAuthNo");
        colCashlessNo.setColumnWidth("25%");
        colCashlessNo.setIsHeaderLink(true);
        colCashlessNo.setHeaderLinkTitle("Sort by: Cashless No");
        colCashlessNo.setDBColumnName("PRE_AUTH_NUMBER");
        addColumn(colCashlessNo);
        
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
        colClaimantName.setColumnWidth("15%");
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
        Column colReceivedDate=new Column("Total Request Amount");
        colReceivedDate.setMethodName("getTotRate");
        colReceivedDate.setColumnWidth("20%");
        colReceivedDate.setIsHeaderLink(true);
        colReceivedDate.setHeaderLinkTitle("Sort by: Total Request Amount");
        colReceivedDate.setDBColumnName("total_entered_rate");
        addColumn(colReceivedDate);

        
       

        //Setting properties for check box
        /*Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);*/
    }// end of public void setTableProperties()

}// end of PreAuthTable class
