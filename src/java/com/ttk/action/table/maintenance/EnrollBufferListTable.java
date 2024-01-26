/**
* @ (#) EnrollBufferListTable.java Jun 19, 2006
* Project 		: TTK HealthCare Services
* File			: EnrollBufferListTable.java
* Author 		: 
* @author 		: 
* Modified by 	:
* Modified date :
* Reason :1216 B cahange request
*/
package com.ttk.action.table.maintenance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class EnrollBufferListTable extends Table {


    public void setTableProperties() {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Ref. No.
        Column colRefNo = new Column("Ref. No.");
        colRefNo.setMethodName("getRefNbr");
        colRefNo.setColumnWidth("15%");
        colRefNo.setIsLink(true);
        colRefNo.setLinkTitle("Edit Ref. No.");
        colRefNo.setIsHeaderLink(true);
        colRefNo.setHeaderLinkTitle("Sort by Ref. No.");
        colRefNo.setDBColumnName("REFERENCE_NO");
        addColumn(colRefNo);
        
        //Setting properties for Buffer Date
        Column colBuffDate = new Column("Buffer Entry Date");
        colBuffDate.setMethodName("getBufferDate1");
        colBuffDate.setColumnWidth("10%");
        colBuffDate.setIsHeaderLink(true);
        colBuffDate.setHeaderLinkTitle("Sort by Buffer Date");
        colBuffDate.setDBColumnName("BUFFER_ADDED_DATE");
        addColumn(colBuffDate);

        //Setting properties for Buffer Amt. (Rs)
        Column colBuffAmt = new Column("Buffer Approved Amt(Member). (Rs)");
        colBuffAmt.setMethodName("getBufferAmt");
        colBuffAmt.setColumnWidth("15%");
        colBuffAmt.setIsHeaderLink(true);
        colBuffAmt.setHeaderLinkTitle("Sort by Buffer Approved Amt. (Rs)");
        colBuffAmt.setDBColumnName("BUFFER_AMT");
        addColumn(colBuffAmt);

      //added for hyundai buffer9
        //Setting the properties for Claim Type
        Column colClaimType = new Column("Claim Type");
        colClaimType.setMethodName("getClaimTypeDesc");
        colClaimType.setColumnWidth("10%");
       // colBufferType.setIsHeaderLink(true);
        //colClaimType.setIsLink(true);
        colClaimType.setHeaderLinkTitle("Sort by: Buffer Type");
        colClaimType.setDBColumnName("CLAIM_TYPE_DESC");
        addColumn(colClaimType); 
        
        
        //Setting the properties for Buffer Type
        Column colBufferType = new Column("Buffer Type");
        colBufferType.setMethodName("getBufferTypeDesc");
        colBufferType.setColumnWidth("10%");
       // colBufferType.setIsHeaderLink(true);
       // colBufferType.setIsLink(true);
        colBufferType.setHeaderLinkTitle("Sort by: Buffer Type");
        colBufferType.setDBColumnName("BUFFER_TYPE_DESC");
        addColumn(colBufferType);
        // end added for hyundai buffer
        
        
        
        //Setting properties for Mode
        Column colMode = new Column("Mode");
        colMode.setMethodName("getModeDesc");
        colMode.setColumnWidth("10%");
        colMode.setIsHeaderLink(true);
        colMode.setHeaderLinkTitle("Sort by Mode");
        colMode.setDBColumnName("BUFFER_MODE_DESC");
        addColumn(colMode);

        //Setting properties member Id
        Column colMemberId = new Column("Enrollment Member Id");
        colMemberId.setMethodName("getEnrollmentId");
        colMemberId.setColumnWidth("20%");
        colMemberId.setIsHeaderLink(true);
        colMemberId.setHeaderLinkTitle("Sort by Member Id");
        colMemberId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colMemberId);
        
     //Setting properties member Id
        Column colUserName = new Column("User Name");
        colUserName.setMethodName("getUserId");
        colUserName.setColumnWidth("15%");
        colUserName.setIsHeaderLink(true);
        colUserName.setHeaderLinkTitle("Sort by User ID");
        colUserName.setDBColumnName("USER_ID");
        addColumn(colUserName);

    }

}

