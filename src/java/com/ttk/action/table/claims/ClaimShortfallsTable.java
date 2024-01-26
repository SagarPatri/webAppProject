/**

* @ (#) ClaimsTable.java Jul 14, 2006
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

package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;
import com.ttk.common.TTKCommon;

public class ClaimShortfallsTable extends Table
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
        
        Column colShortfallNo=new Column("Shortfall No.");
        colShortfallNo.setMethodName("getShortfallNo");
        colShortfallNo.setColumnWidth("14%");
        colShortfallNo.setIsLink(true);
        colShortfallNo.setIsHeaderLink(true);
        colShortfallNo.setHeaderLinkTitle("Sort by: Shortfall No.");
        colShortfallNo.setDBColumnName("SHORTFALL_ID");
        addColumn(colShortfallNo);
      //Setting properties for Claim No.
        Column colInvoiceNo=new Column("Invoice No.");
        colInvoiceNo.setMethodName("getInvoiceNo");
        colInvoiceNo.setColumnWidth("14%");
       // colInvoiceNo.setIsLink(true);
        colInvoiceNo.setIsHeaderLink(true);
        colInvoiceNo.setHeaderLinkTitle("Sort by: Invoice No.");
        colInvoiceNo.setDBColumnName("INVOICE_NUMBER");
        addColumn(colInvoiceNo);
        
      //Setting properties for Claim No.
        Column colBatchNo=new Column("Batch No.");
        colBatchNo.setMethodName("getBatchNo");
        colBatchNo.setColumnWidth("14%");
        colBatchNo.setIsHeaderLink(true);
        colBatchNo.setHeaderLinkTitle("Sort by: Batch No.");
        colBatchNo.setDBColumnName("BATCH_NO");
        addColumn(colBatchNo);

        //Setting properties for Claim No.
        Column colClaimNo=new Column("Claim No.");
        colClaimNo.setMethodName("getClaimNo");
        colClaimNo.setColumnWidth("14%");
        //colClaimNo.setIsLink(true);
        colClaimNo.setIsHeaderLink(true);
        colClaimNo.setHeaderLinkTitle("Sort by: Claim No.");
        colClaimNo.setDBColumnName("CLAIM_NUMBER");
        addColumn(colClaimNo);

        //Setting properties for  Enrollment Id.
        Column colEnrollmentId=new Column("Enrollment Id");
        colEnrollmentId.setMethodName("getEnrollmentID");
        colEnrollmentId.setColumnWidth("14%");
        colEnrollmentId.setIsHeaderLink(true);
        colEnrollmentId.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentId);
        
        Column coldhpoMemberId=new Column("Member Id");
        coldhpoMemberId.setMethodName("getDhpoMemberId");
        coldhpoMemberId.setColumnWidth("14%");
        coldhpoMemberId.setIsHeaderLink(true);
        coldhpoMemberId.setHeaderLinkTitle("Sort by: DHPO Member Id");
        coldhpoMemberId.setDBColumnName("DHPO_MEMBER_ID");
        addColumn(coldhpoMemberId);
        
        //Setting properties for  Policy NO.
        Column colPolicyNo=new Column("Policy No");
        colPolicyNo.setMethodName("getPolicyNo");
        colPolicyNo.setColumnWidth("14%");
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setHeaderLinkTitle("Sort by: Policy No");
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);
      
      //Setting properties for Claim Type.
        Column colClaimType=new Column("Claim Type");
        colClaimType.setMethodName("getClaimType");
        colClaimType.setColumnWidth("10%");
        colClaimType.setIsHeaderLink(true);
        colClaimType.setHeaderLinkTitle("Sort by: Claim Type");
        colClaimType.setDBColumnName("CLAIM_TYPE");
        addColumn(colClaimType);
        
        //Setting properties for Assigned To Type.
        Column colAssigned=new Column("Assigned TO");
        colAssigned.setMethodName("getClaimType");
        colAssigned.setColumnWidth("10%");
        colAssigned.setIsHeaderLink(true);
        colAssigned.setHeaderLinkTitle("Sort by: Assigned TO");
        colAssigned.setDBColumnName("STATUS");// CONTACT_NAME
        addColumn(colAssigned);
        //Setting properties for  Adm. Date.
        Column colRecDate=new Column("Rec. Date");
        colRecDate.setMethodName("getReceivedDateAsString");
        colRecDate.setColumnWidth("10%");
        colRecDate.setIsHeaderLink(true);
        colRecDate.setHeaderLinkTitle("Sort by:Rec. Date");
        colRecDate.setDBColumnName("RECEIVED_DATE");
        addColumn(colRecDate);

      		 //Koc Decoupling
        Column colStatus=new Column("Status.");
        colStatus.setMethodName("getStatus");
        colStatus.setColumnWidth("5%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status.");
        colStatus.setDBColumnName("STATUS");
        addColumn(colStatus);        

       /* //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        colSelect.setColumnWidth("1%");
        addColumn(colSelect);*/
    } //end of setTableProperties()
}// end of ClaimsTable class


