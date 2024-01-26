/**
 * @ (#) PreAuthTable.javaMay 5, 2006
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

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class PreAuthEnhancementTable extends Table
{
    /**
	 * 
	 */
	

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(11);
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
        colImage1.setDBColumnName("PRIORITY_GENERAL_TYPE_ID");//PRIORITY_GENERAL_TYPE_ID
        colImage1.setColumnWidth("1%");
        addColumn(colImage1);
        
        //Setting properties for Cashless No.
        Column colPreAuthNo=new Column("Pre-Auth No.");
        colPreAuthNo.setMethodName("getPreAuthNo");
        colPreAuthNo.setColumnWidth("20%");
        colPreAuthNo.setIsLink(true);
        colPreAuthNo.setIsHeaderLink(true);
        colPreAuthNo.setImageName("getShortfallImageName");
        colPreAuthNo.setImageTitle("getShortfallImageTitle");
		colPreAuthNo.setImageName2("getInvImageName"); 
        colPreAuthNo.setImageTitle2("getInvImageTitle");
        colPreAuthNo.setHeaderLinkTitle("Sort by: Pre-Auth No.");
        colPreAuthNo.setDBColumnName("PRE_AUTH_NUMBER");
        addColumn(colPreAuthNo);
        
        Column colStatus=new Column("Status");
        colStatus.setMethodName("getStatusTypeID");
        colStatus.setColumnWidth("6%");
        colStatus.setIsHeaderLink(true);
       // colStatus.setHeaderLinkTitle("Sort by: Hospital Name");
        colStatus.setDBColumnName("PAT_STATUS_TYPE_ID");
        addColumn(colStatus);

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
        colEnrollmentId.setColumnWidth("20%");
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
        colClaimantName.setDBColumnName("MEM_NAME");
        addColumn(colClaimantName);

        //Setting properties for  Assigned To.
        Column colAssignedTo=new Column("Assigned To");
        colAssignedTo.setMethodName("getAssignedTo");
        colAssignedTo.setColumnWidth("8%");
        colAssignedTo.setIsHeaderLink(true);
        colAssignedTo.setHeaderLinkTitle("Sort by: Assigned To");
        colAssignedTo.setDBColumnName("CONTACT_NAME");
        addColumn(colAssignedTo);

        //Setting properties for  Received Date.
        Column colReceivedDate=new Column("Received Date");
        colReceivedDate.setMethodName("getReceivedDateAsString");
        colReceivedDate.setColumnWidth("10%");
        colReceivedDate.setIsHeaderLink(true);
        colReceivedDate.setHeaderLinkTitle("Sort by: Received Date");
        colReceivedDate.setDBColumnName("PAT_RECEIVED_DATE");
        addColumn(colReceivedDate);
        
      //Setting properties for  Admission Date.
        Column colCllaimAdmDate=new Column("Admission Date");
        colCllaimAdmDate.setMethodName("getClaimAdmnDate");
        colCllaimAdmDate.setColumnWidth("10%");
        colCllaimAdmDate.setIsHeaderLink(true);
        colCllaimAdmDate.setHeaderLinkTitle("Sort by: Admission Date");
        colCllaimAdmDate.setDBColumnName("HOSPITALIZATION_DATE");
        addColumn(colCllaimAdmDate);
        
      //Setting properties for  Discharge Date.
        Column colCllaimDisrgDate=new Column("Discharge Date");
        colCllaimDisrgDate.setMethodName("getClaimDisrgDate");
        colCllaimDisrgDate.setColumnWidth("10%");
        colCllaimDisrgDate.setIsHeaderLink(true);
        colCllaimDisrgDate.setHeaderLinkTitle("Sort by: Discharge Date");
        colCllaimDisrgDate.setDBColumnName("DISCHARGE_DATE");
        addColumn(colCllaimDisrgDate);

        //Setting properties for image Preauth
       /* Column colImage2 = new Column("");
        colImage2.setIsImage(true);
        colImage2.setIsImageLink(true);
        colImage2.setImageName("getPreAuthImageName");
        colImage2.setImageTitle("getPreAuthImageTitle");
		colImage2.setVisibility(false);
        addColumn(colImage2);

        //Setting properties for image Assign
        Column colImage3 = new Column("");
        colImage3.setIsImage(true);
        colImage3.setIsImageLink(true);
        colImage3.setImageName("getAssignImageName");
        colImage3.setImageTitle("getAssignImageTitle");
        colImage3.setVisibility(false);
        addColumn(colImage3);
		
		//Koc 11 koc11
        Column colImage5 = new Column("");
        colImage5.setIsImage(true);
        colImage5.setIsImageLink(true);
        colImage5.setImageName("getInvestigationImageName");
        colImage5.setImageTitle("getInvestigationImageTitle");        
        colImage5.setVisibility(false);
        addColumn(colImage5); */

        //Setting properties for check box
       /* Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);*/
    }// end of public void setTableProperties()

}// end of PreAuthEnhancementTable class
