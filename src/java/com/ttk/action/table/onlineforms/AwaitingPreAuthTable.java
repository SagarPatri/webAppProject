/**BAJAj........................
 * @ (#) AwaitingClaimsTable.java Sep 20, 2005   
 *
 */
package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of sbb_vendor table
 */
public class AwaitingPreAuthTable extends Table {
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
	        Column colPreAuth=new Column("PreAuth No.");
	        colPreAuth.setMethodName("getPreAuthNo");
	        colPreAuth.setColumnWidth("15%");
	        colPreAuth.setIsLink(true);
	        colPreAuth.setIsHeaderLink(true);
	       // colClaimNo.setImageName("");
	       // colClaimNo.setImageTitle("");
	        colPreAuth.setVisibility(true);
	        colPreAuth.setHeaderLinkTitle("Sort by: PreAuth No.");
	        colPreAuth.setDBColumnName("PRE_AUTH_NUMBER");
	        addColumn(colPreAuth);
	        
	        
	        //Setting properties for  Enrollment Id.
	        Column colEnrollmentId=new Column("Enrollment Id");
	        colEnrollmentId.setMethodName("getEnrollmentID");
	        colEnrollmentId.setColumnWidth("20%");
	        colEnrollmentId.setIsHeaderLink(true);
	        colEnrollmentId.setHeaderLinkTitle("Sort by: Enrollment Id");
	        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
	        addColumn(colEnrollmentId);
   
	        //Setting properties for  Claim Amount.
	        Column colClaimAmount=new Column("Rec. PreAuth Amount");
	        colClaimAmount.setMethodName("getTotalApprovedAmount");
	        colClaimAmount.setColumnWidth("15%");
	        colClaimAmount.setIsHeaderLink(true);
	        colClaimAmount.setHeaderLinkTitle("Sort by: Recommended Claim Amount");
	        colClaimAmount.setDBColumnName("TOTAL_APP_AMOUNT");
	        addColumn(colClaimAmount);

			//Setting properties for  Member Name.
	        Column colClaimantName=new Column("Member Name");
	        colClaimantName.setMethodName("getClaimantName");
	        colClaimantName.setColumnWidth("15%");
	        colClaimantName.setIsHeaderLink(true);
	        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
	        colClaimantName.setDBColumnName("CLAIMANT_NAME");
	        addColumn(colClaimantName);
			
			 //Setting properties for  ClaimReommendedDate
	        Column colClaimReommendedDate=new Column("PreAuth Rec. Date");
	        colClaimReommendedDate.setMethodName("getClaimRecommendedDate");
	        colClaimReommendedDate.setColumnWidth("10%");
	        colClaimReommendedDate.setIsHeaderLink(true);
	        colClaimReommendedDate.setHeaderLinkTitle("Sort preAuth Reommended Date");
	        colClaimReommendedDate.setDBColumnName("DECISION_DATE");
	        addColumn(colClaimReommendedDate);
			
	        //Setting properties for Hospital Name.
	        Column colHospitalName=new Column("Hospital Name");
	        colHospitalName.setMethodName("getHospitalName");
	        colHospitalName.setColumnWidth("15%");
	        colHospitalName.setIsHeaderLink(true);
	        colHospitalName.setHeaderLinkTitle("Sort by: Hospital Name");
	        colHospitalName.setDBColumnName("HOSP_NAME");
	        addColumn(colHospitalName);

	        //Setting properties for  Adm. Date.
	        Column colAdmDate=new Column("Adm. Date");
	        colAdmDate.setMethodName("getHospDate");
	        colAdmDate.setColumnWidth("10%");
	        colAdmDate.setIsHeaderLink(true);
	        colAdmDate.setHeaderLinkTitle("Sort by: Adm. Date");
	        colAdmDate.setDBColumnName("DATE_OF_HOSPITALIZATION");
	        addColumn(colAdmDate);

	        
	       //Setting properties for Status.
	        Column colPreAuthStatus=new Column("TPA Status");
	        colPreAuthStatus.setMethodName("getPreAuthClaimStatus");
	        colPreAuthStatus.setColumnWidth("10%");
	        colPreAuthStatus.setIsHeaderLink(true);
	        colPreAuthStatus.setHeaderLinkTitle("Sort by: Status");
	        colPreAuthStatus.setDBColumnName("PAT_STATUS");
	        addColumn(colPreAuthStatus);
	        
	        
	       //Setting properties for check box
	        Column colSelect = new Column("Select");
	        colSelect.setComponentType("checkbox");
	        colSelect.setComponentName("chkopt");
	        addColumn(colSelect);
     }
}

