package com.ttk.action.table.onlineforms.providerLogin; 

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class OverDueReportTable extends Table{

	
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

        
        //Setting properties for DATE
        Column colDate = new Column("Received Date");//0
        colDate.setMethodName("getReceivedDate");
        colDate.setColumnWidth("10%");
        //colProductName.setIsLink(true);
        colDate.setDBColumnName("DATE");
        addColumn(colDate);
        
      //Setting properties for InvoiceNumber
        Column colInvoiceNumber = new Column("Invoice Number");//1
        colInvoiceNumber.setMethodName("getInvNo");
        colInvoiceNumber.setColumnWidth("10%");
        colInvoiceNumber.setDBColumnName("INVOICE_NUMBER");
        addColumn(colInvoiceNumber);
        
      //Setting properties for tRAETING Date
        Column colTreatingDate = new Column("Treating Date");//2
        colTreatingDate.setMethodName("getTreatmentDate");
        colTreatingDate.setColumnWidth("10%");
        colTreatingDate.setDBColumnName("TREATING_Date");
        addColumn(colTreatingDate);
        
      //Setting properties for DATE
        Column colPatientName = new Column("Patient Name");//3
        colPatientName.setMethodName("getPatientName");
        colPatientName.setColumnWidth("15%");
        colPatientName.setDBColumnName("PATIENT_NAME");
        addColumn(colPatientName);

        
      //Setting properties for Payer Name
        Column colPayerName = new Column("Payer Name");//6
        colPayerName.setMethodName("getInsCompName");
        colPayerName.setColumnWidth("10%");
        colPayerName.setDBColumnName("PAYER_NAME");
        addColumn(colPayerName);
        
      //Setting properties for BenefitType
        Column colTotalAmountClaimed = new Column("Total Amount Claimed ");//7
        colTotalAmountClaimed.setMethodName("getClaimedAmt");
        colTotalAmountClaimed.setColumnWidth("10%");
        colTotalAmountClaimed.setDBColumnName("TOTAL_AMOUNT_CLAIMED");
        addColumn(colTotalAmountClaimed);
        
      //Setting properties for tRAETING dOCTOR
        Column colDueDate = new Column("Due Date");//8
        colDueDate.setMethodName("getDueDate");
        colDueDate.setColumnWidth("10%");
        colDueDate.setDBColumnName("DUE_DATE");
        addColumn(colDueDate);
        
      //Setting properties for Status
        Column colOverDueBy = new Column("Over Due By");//9
        colOverDueBy.setMethodName("getOveDueBy");
        colOverDueBy.setColumnWidth("10%");
        colOverDueBy.setDBColumnName("OVER_DUE_BY");
       // colOverDueBy.setIsLink(true);
        addColumn(colOverDueBy);
        
        
        
	}

}
