package com.ttk.action.table.onlineforms.partnerLogin; 

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;
  
public class PartnerClaimSearchTable extends Table{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	
	@Override
	public void setTableProperties() {

		setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Claim Submitted Date
        Column colDate = new Column("Claim Submitted Date");//1
        colDate.setMethodName("getClaimSubmittedDate");
        colDate.setColumnWidth("10%");
        //colProductName.setIsLink(true);
        colDate.setDBColumnName("CLAIM_SUBMITTED_DATE");
        addColumn(colDate);
        
        //Setting properties for treatment Date
        Column colTreatmentDate = new Column("Treatment Date");//2
        colTreatmentDate.setMethodName("getTreatmentDate");
        colTreatmentDate.setColumnWidth("7%");
        colTreatmentDate.setDBColumnName("TREATMENT_DATE");
        addColumn(colTreatmentDate);
        
        //Setting properties for Batch Number
        Column colBatchNumber = new Column("Batch Number");//2
        colBatchNumber.setMethodName("getBatchNo");
        colBatchNumber.setColumnWidth("10%");
        colBatchNumber.setDBColumnName("BATCH_NUMBER");
        addColumn(colBatchNumber);
        
        
        //Setting properties for Member Id
        Column colMemberId = new Column("Vidal Enrollment Id.");//3
        colMemberId.setMethodName("getPatientCardId");
        colMemberId.setColumnWidth("12%");
       // colMemberId.setIsLink(true);
        colMemberId.setDBColumnName("MEMBER_ID");
        addColumn(colMemberId);
        
        //Setting properties for Patient Name
        Column colPatientName = new Column("Patient Name");//4
        colPatientName.setMethodName("getPatientName");
        colPatientName.setColumnWidth("10%");
        colPatientName.setDBColumnName("PATIENT_NAME");
        addColumn(colPatientName);
        
        
      //Setting properties for Claim Number
        Column colClaimNo = new Column("Claim Number");//5
        colClaimNo.setMethodName("getPreAuthNo");
        colClaimNo.setColumnWidth("10%");
        colClaimNo.setDBColumnName("CLAIM_NUMBER");
        addColumn(colClaimNo);
        
      //Setting properties for Claimed amount
        Column colClaimedAmount = new Column("Claimed amount");//6
        colClaimedAmount.setMethodName("getClaimedAmount");
        colClaimedAmount.setColumnWidth("5%");
        colClaimedAmount.setDBColumnName("CLAIMED_AMOUNT");
        addColumn(colClaimedAmount);
        
        
        //Setting properties for InvoiceNumber
        Column colInvoiceNumber = new Column("Invoice Number");//7
        colInvoiceNumber.setMethodName("getInvoiceNo");
        colInvoiceNumber.setColumnWidth("7%");
        colInvoiceNumber.setDBColumnName("INVOICE_NUMBER");
        addColumn(colInvoiceNumber);
        
        
        //Setting properties for Status
        Column colStatus = new Column("Status");//8
        colStatus.setMethodName("getStatus");
        colStatus.setColumnWidth("7%");
        colStatus.setDBColumnName("STATUS");
        colStatus.setIsLink(true);
        addColumn(colStatus);
             
     //Setting properties for Status
        Column colProvider = new Column("Provider Name");//8
        colProvider.setMethodName("getProviderName");
        colProvider.setColumnWidth("10%");
        colProvider.setDBColumnName("Provider_Name");
        addColumn(colProvider);
        
      
        //Setting properties for Status
        Column colCountry = new Column("Provider Country");//8
        colCountry.setMethodName("getCountryName");
        colCountry.setColumnWidth("15%");
        colCountry.setDBColumnName("Country");
      //  colStatus.setIsLink(true);
        addColumn(colCountry);
        
	}

}
