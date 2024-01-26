package com.ttk.action.table.onlineforms.providerLogin; 

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class BatchInvoiceTable extends Table{

	
	private static final long serialVersionUID = 1L;

	/**
     * This creates the column properties objects for each and
     * every column and adds the column object to the table
     */
	
	@Override
	public void setTableProperties() {

		setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        
     //Setting properties for SERIEL_NO  
        /*Column colSERIEL_NO = new Column("Sl No");//0
        colSERIEL_NO.setMethodName("getSlno");
        colSERIEL_NO.setColumnWidth("5%");
        colSERIEL_NO.setDBColumnName("SERIEL_NO");
        addColumn(colSERIEL_NO);*/
        
      //Setting properties for PAYER_NAME
        Column colPAYER_NAME  = new Column("Payer Name");//1
        colPAYER_NAME.setMethodName("getInsCompName");
        colPAYER_NAME.setColumnWidth("25%");
        colPAYER_NAME.setDBColumnName("PAYER_NAME");
        addColumn(colPAYER_NAME);
        
      //Setting properties for INVOICE_NUMBER
        Column colBATCH_NUMBER = new Column("Batch Number");//2
        colBATCH_NUMBER.setMethodName("getBatchNo");
        colBATCH_NUMBER.setColumnWidth("15%");
        colBATCH_NUMBER.setDBColumnName("BATCH_NO");
        addColumn(colBATCH_NUMBER);
        
      //Setting properties for INVOICE_NUMBER
        Column colINVOICE_NUMBER = new Column("Invoice Number");//2
        colINVOICE_NUMBER.setMethodName("getInvNo");
        colINVOICE_NUMBER.setColumnWidth("15%");
        colINVOICE_NUMBER.setIsLink(true);
        colINVOICE_NUMBER.setDBColumnName("INVOICE_NUMBER");
        addColumn(colINVOICE_NUMBER);
        
      //Setting properties for Treatment Date
        /*Column colTreatmentDate = new Column("Treatment Date");//3
        colTreatmentDate.setMethodName("getTreatmentDate");
        colTreatmentDate.setColumnWidth("5%");
        colTreatmentDate.setDBColumnName("TREATMENT_DATE");
        addColumn(colTreatmentDate);*/
        
      //Setting properties for Benefit Type
       /* Column colBenefitType = new Column("Benefit Type");//4
        colBenefitType.setMethodName("getBenefitType");
        colBenefitType.setColumnWidth("5%");
        colBenefitType.setDBColumnName("BENEFIT_TYPE");
        addColumn(colBenefitType);*/
        
      //Setting properties for ClmRecdDate
        Column colClmRecdDate = new Column("Claim Recdeived Date");//5
        colClmRecdDate.setMethodName("getReceivedDate");
        colClmRecdDate.setColumnWidth("15%");
        colClmRecdDate.setDBColumnName("CLM_RECD_DATE");
        addColumn(colClmRecdDate);
        
      //Setting properties for Service Code
       /* Column colServiceCode = new Column("Service Code");//6
        colServiceCode.setMethodName("getServiceCode");
        colServiceCode.setColumnWidth("5%");
        colServiceCode.setDBColumnName("SERVICE_CODE");
        addColumn(colServiceCode);*/
        
      //Setting properties for Service Name
        /*Column colSServiceName = new Column("Service Name");//7
        colSServiceName.setMethodName("getServiceName");
        colSServiceName.setColumnWidth("5%");
        colSServiceName.setDBColumnName("SERVICE_NAME");
        addColumn(colSServiceName);*/
        
      //Setting properties for PatientName
        /*Column colPatientName = new Column("Patient Name");//8
        colPatientName.setMethodName("getPatientName");
        colPatientName.setColumnWidth("5%");
        colPatientName.setDBColumnName("PATIENT_NAME");
		addColumn(colPatientName);*/
        
      //Setting properties for Total Claimed Amount
        /*Column colSTotalClaimedAmount = new Column("Ttl Clmd Amt");//9
        colSTotalClaimedAmount.setMethodName("getTotalClaimedAmount");
        colSTotalClaimedAmount.setColumnWidth("5%");
        colSTotalClaimedAmount.setDBColumnName("TOTAL_CLAIMED_AMOUNT");
        addColumn(colSTotalClaimedAmount);*/
        
      //Setting properties for Discount
       /* Column colDiscount = new Column("Discount");//10
        colDiscount.setMethodName("getDiscount");
        colDiscount.setColumnWidth("5%");
        colDiscount.setDBColumnName("Discount");
		addColumn(colDiscount);*/
        
      //Setting properties for Total Claimed Amount
        /*Column colSCopay = new Column("Copay");//10
        colSCopay.setMethodName("getCopay");
        colSCopay.setColumnWidth("5%");
        colSCopay.setDBColumnName("Copay");
        addColumn(colSCopay);*/
        
      //Setting properties for Ttl Net Clmd
        /*Column TtlNetClmd = new Column("Ttl Net Clmd");//11
        TtlNetClmd.setMethodName("getTtlNetClmd");
        TtlNetClmd.setColumnWidth("5%");
        TtlNetClmd.setDBColumnName("TtlNetClmd");
		addColumn(TtlNetClmd);*/
        
      //Setting properties for Total Ttl Appr Clmd
        /*Column TtlApprAmt = new Column("Ttl Appr Amt");//12
        TtlApprAmt.setMethodName("getTtlApprAmt");
        TtlApprAmt.setColumnWidth("5%");
        TtlApprAmt.setDBColumnName("TtlApprAmt");
        addColumn(TtlApprAmt);*/
        
      //Setting properties for Reason
        /*Column Reason = new Column("Reason");//13
        Reason.setMethodName("getReason");
        Reason.setColumnWidth("5%");
        Reason.setDBColumnName("Reason");
		addColumn(Reason);*/
        
      //Setting properties for Diagnosis Code
       /* Column DiagCode = new Column("Diag Code");//14
        DiagCode.setMethodName("getDiagCode");
        DiagCode.setColumnWidth("5%");
        DiagCode.setDBColumnName("DIAG_CODE");
		addColumn(DiagCode);*/
        
      //Setting properties for Diagnosis
        /*Column Diag = new Column("Diag");//15
        Diag.setMethodName("getDiag");
        Diag.setColumnWidth("5%");
        Diag.setDBColumnName("Diag");
		addColumn(Diag);*/
        
      //Setting properties for Status
        Column Status = new Column("Status");//16
        Status.setMethodName("getClaimStatus");
        Status.setColumnWidth("15%");
        Status.setDBColumnName("Status");
		addColumn(Status);
        
      //Setting properties for Chq_No
       /* Column Chq_No = new Column("Chq No.");//17
        Chq_No.setMethodName("getChqNo");
        Chq_No.setColumnWidth("5%");
        Chq_No.setDBColumnName("Chq_No");
		addColumn(Chq_No);*/
        
      //Setting properties for ChqDt
       /* Column ChqDt = new Column("Chq Dt.");//18
        ChqDt.setMethodName("getChqDt");
        ChqDt.setColumnWidth("5%");
        ChqDt.setDBColumnName("ChqDt");
		addColumn(ChqDt);*/
        
      //Setting properties for Discount
        /*Column ChqAmt = new Column("Chq Amt.");//19
        ChqAmt.setMethodName("getChqAmt");
        ChqAmt.setColumnWidth("5%");
        ChqAmt.setDBColumnName("ChqAmt");
		addColumn(ChqAmt);*/
        
      //Setting properties for DisRemarkscount
       /* Column Remarks = new Column("Remarks");//20
        Remarks.setMethodName("getRemarks");
        Remarks.setColumnWidth("5%");
        Remarks.setDBColumnName("Remarks");
		addColumn(Remarks);*/

	}

}
