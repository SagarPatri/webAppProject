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

public class ClaimsTable extends Table
{
	
	public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);        
      //Setting properties for Claim No.  1
        Column colInvoiceNo=new Column("Invoice No.");
        colInvoiceNo.setMethodName("getInvoiceNo");
        colInvoiceNo.setColumnWidth("10%");
        colInvoiceNo.setIsLink(true);
        colInvoiceNo.setIsHeaderLink(true);
        colInvoiceNo.setImageName("");
        colInvoiceNo.setImageTitle("");
        colInvoiceNo.setImageName2("getInvImageName"); 
        colInvoiceNo.setImageTitle2("getInvImageTitle");
        colInvoiceNo.setHeaderLinkTitle("Sort by: Invoice No.");
        colInvoiceNo.setDBColumnName("INVOICE_NUMBER");
        addColumn(colInvoiceNo);
        
      //Setting properties for Claim No.  2
        Column colBatchNo=new Column("Batch No.");
        colBatchNo.setMethodName("getBatchNo");
        colBatchNo.setColumnWidth("10%");
        colBatchNo.setIsHeaderLink(true);
        colBatchNo.setHeaderLinkTitle("Sort by: Batch No.");
        colBatchNo.setDBColumnName("BATCH_NO");
        addColumn(colBatchNo);
        
      //Setting properties for Claim No. 3
        Column colSubBatchNo=new Column("Sub Batch No.");
        colSubBatchNo.setMethodName("getSubBatchID");
        colSubBatchNo.setColumnWidth("10%");
        colSubBatchNo.setIsHeaderLink(true);
        colSubBatchNo.setHeaderLinkTitle("Sort by: Sub Batch No.");
        colSubBatchNo.setDBColumnName("SUB_BATCH_ID");
        addColumn(colSubBatchNo);

        //Setting properties for Claim No.  4
        Column colClaimNo=new Column("Claim No.");
        colClaimNo.setMethodName("getClaimNo");
        colClaimNo.setColumnWidth("14%");
        //colClaimNo.setIsLink(true);
        colClaimNo.setIsHeaderLink(true);
        colClaimNo.setHeaderLinkTitle("Sort by: Claim No.");
        colClaimNo.setDBColumnName("CLAIM_NUMBER");
        addColumn(colClaimNo);

        //Setting properties for  Enrollment Id. 5
        Column colEnrollmentId=new Column("Enrollment Id");
        colEnrollmentId.setMethodName("getEnrollmentID");
        colEnrollmentId.setColumnWidth("15%");
        colEnrollmentId.setIsHeaderLink(true);
        colEnrollmentId.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentId);
        
        //Setting properties for  Enrollment Id. 5
        Column dhpoMemberId=new Column("Member Id");
        dhpoMemberId.setMethodName("getDhpoMemberId");
        dhpoMemberId.setColumnWidth("15%");
        dhpoMemberId.setIsHeaderLink(true);
        dhpoMemberId.setHeaderLinkTitle("Sort by: DHPO Member Id");
        dhpoMemberId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(dhpoMemberId);
        
       //6
        Column colSubmissionType=new Column("Submission Category");
        colSubmissionType.setMethodName("getProcessType");
        colSubmissionType.setColumnWidth("8%");
        colSubmissionType.setIsHeaderLink(true);
        colSubmissionType.setHeaderLinkTitle("Sort by: Submission Type");
        colSubmissionType.setDBColumnName("submission_catogory");
        addColumn(colSubmissionType);
        
        
        
        
        
      //Setting properties for  Alternate Id.   7
        Column colAlternateId=new Column("Alternate Id");
        colAlternateId.setMethodName("getStrAlternateID");				// vo  get() method name
        colAlternateId.setColumnWidth("10%");
        colAlternateId.setIsHeaderLink(true);
        colAlternateId.setHeaderLinkTitle("Sort by: Alternate Id");
        colAlternateId.setDBColumnName("TPA_ALTERNATE_ID");			// database column name
        addColumn(colAlternateId);
        
        // 8
        Column colEmiratesId=new Column("Civil Id");
        colEmiratesId.setMethodName("getsEmiratesID");				
        colEmiratesId.setColumnWidth("14%");
        colEmiratesId.setIsHeaderLink(true);
        colEmiratesId.setHeaderLinkTitle("Sort by: Emirates Id");	
        colEmiratesId.setDBColumnName("emirate_id");				
        addColumn(colEmiratesId);
        
        
        //Setting properties for  Policy NO.
     /*   Column colPolicyNo=new Column("Policy No");
        colPolicyNo.setMethodName("getPolicyNo");
        colPolicyNo.setColumnWidth("14%");
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setHeaderLinkTitle("Sort by: Policy No");
        colPolicyNo.setDBColumnName("POLICY_NO");
        addColumn(colPolicyNo);
*/
        //Setting properties for Hospital Name.  9
        Column colHospitalName=new Column("Provider Name");
        colHospitalName.setMethodName("getHospitalName");
        colHospitalName.setColumnWidth("14%");
        colHospitalName.setIsHeaderLink(true);
        colHospitalName.setHeaderLinkTitle("Sort by: Provider Name");
        colHospitalName.setDBColumnName("HOSP_NAME");
        addColumn(colHospitalName);
       
        //Setting properties for  Member Name.  10
        Column colClaimantName=new Column("Member Name");
        colClaimantName.setMethodName("getClaimantName");
        colClaimantName.setColumnWidth("10%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("MEM_NAME");
        addColumn(colClaimantName);

      //Setting properties for Claim Type.  11
        Column colClaimType=new Column("Claim Type");
        colClaimType.setMethodName("getClaimType");
        colClaimType.setColumnWidth("10%");
        colClaimType.setIsHeaderLink(true);
        colClaimType.setHeaderLinkTitle("Sort by: Claim Type");
        colClaimType.setDBColumnName("CLAIM_TYPE");
        addColumn(colClaimType);
        
        //Setting properties for Assigned To Type. 12
        Column colAssigned=new Column("Assigned TO");
        colAssigned.setMethodName("getAssignedTo");
        colAssigned.setColumnWidth("8%");
        colAssigned.setIsHeaderLink(true);
        colAssigned.setHeaderLinkTitle("Sort by: Assigned TO");
        colAssigned.setDBColumnName("CONTACT_NAME");
        addColumn(colAssigned);
        //Setting properties for  Adm. Date.  13
        Column colRecDate=new Column("Rec. Date");
        colRecDate.setMethodName("getReceivedDateAsString");
        colRecDate.setColumnWidth("10%");
        colRecDate.setIsHeaderLink(true);
        colRecDate.setHeaderLinkTitle("Sort by:Rec. Date");
        colRecDate.setDBColumnName("RECEIVED_DATE");
        addColumn(colRecDate);

        
        //14
        Column colmode=new Column("Mode of Claim");
        colmode.setMethodName("getModeOfClaim");
        colmode.setColumnWidth("10%");
        colmode.setIsHeaderLink(true);
        colmode.setDBColumnName("MODE_C");
        addColumn(colmode);
        
        
        
        
        
      		 //Koc Decoupling   15
        Column colStatus=new Column("Status.");
        colStatus.setMethodName("getStatus");
        colStatus.setColumnWidth("5%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status.");
        colStatus.setDBColumnName("DESCRIPTION");
        addColumn(colStatus);        

        //Setting properties for check box   16
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        colSelect.setColumnWidth("1%");
        addColumn(colSelect);
    } //end of setTableProperties()
}// end of ClaimsTable class


