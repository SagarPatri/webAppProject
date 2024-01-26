package com.ttk.action.table.onlineforms.providerLogin;


import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ClaimSummarySearchTable extends Table {
	

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		
		@Override
		public void setTableProperties() {
			
			setRowCount(100);
	        setCurrentPage(1);
	        setPageLinkCount(10);
			
	        
	        Column colClaimNo = new Column("Claim No");//1
	        colClaimNo.setMethodName("getClaimnumber");
	        colClaimNo.setColumnWidth("7%");
	        colClaimNo.setDBColumnName("claim_number");
	        addColumn(colClaimNo);
	        
	        
	        
	        Column colInvoiceNumber = new Column("Invoice No");//2
	        colInvoiceNumber.setMethodName("getInvoiceNo");
	        colInvoiceNumber.setColumnWidth("5%");
	        colInvoiceNumber.setDBColumnName("invoice_number");
	        addColumn(colInvoiceNumber);
	        
	        
	        Column colReceivedDate = new Column("Received Date");//3
	        colReceivedDate.setMethodName("getFromDate");
	        colReceivedDate.setColumnWidth("5%");
	        colReceivedDate.setDBColumnName("received_date");
	        addColumn(colReceivedDate);
	        
	        
	        Column colTreatmentDate = new Column("Treatment Date");//4
	        colTreatmentDate.setMethodName("getToDate");
	        colTreatmentDate.setColumnWidth("5%");
	        colTreatmentDate.setDBColumnName("treatment_date");
	        addColumn(colTreatmentDate);
	        
	        Column colPatientName = new Column("Patient Name");//5
	        colPatientName.setMethodName("getPatientName");
	        colPatientName.setColumnWidth("5%");
	        colPatientName.setDBColumnName("patient_name");
	        addColumn(colPatientName);
	        
	        Column colPatientCardId = new Column("Patient Id Card No");//6
	        colPatientCardId.setMethodName("getPatient_idcard_no");
	        colPatientCardId.setColumnWidth("5%");
	        colPatientCardId.setDBColumnName("patient_idcard_no");
	        addColumn(colPatientCardId);
	        
	        
	        Column colCorporateName = new Column("Corporate Name");//7
	        colCorporateName.setMethodName("getCorporate_name");
	        colCorporateName.setColumnWidth("8%");
	        colCorporateName.setDBColumnName("Corporate_name");
	        addColumn(colCorporateName);
	        
	        
	        Column Diagnosis = new Column("Diagnosis_Code");//8
	        Diagnosis.setMethodName("getDiagnosis");
	        Diagnosis.setColumnWidth("10%");
	        Diagnosis.setDBColumnName("patient_name");
	        addColumn(Diagnosis);
	        
	        
	        
	        Column colTreatingDoctor = new Column("Doctor");//9
	        colTreatingDoctor.setMethodName("getDoctor_name");
	        colTreatingDoctor.setColumnWidth("5%");
	        colTreatingDoctor.setDBColumnName("doctor");
	        addColumn(colTreatingDoctor);
	        
	        Column colReqAmt = new Column("Req.Amt");//10
	        colReqAmt.setMethodName("getReq_amount");
	        colReqAmt.setColumnWidth("5%");
	        colReqAmt.setDBColumnName("req_amount");
	        addColumn(colReqAmt);
	        
	        
	      /*  Column colAgreedTariff = new Column("Agreed Tariff");//11
	        colAgreedTariff.setMethodName("getAgreed_tariff");
	        colAgreedTariff.setColumnWidth("5%");
	        colAgreedTariff.setDBColumnName("Agreed_Tariff");
	        addColumn(colAgreedTariff);
	        
	        
	        Column colDiscount = new Column("Discount");//12
	        colDiscount.setMethodName("getDiscount");
	        colDiscount.setColumnWidth("5%");
	        colDiscount.setDBColumnName("discount_amount");
	        addColumn(colDiscount);
	        
	        
	        Column colDiscountGross = new Column("Discounted Gross");//13
	        colDiscountGross.setMethodName("getDiscounted_gross");
	        colDiscountGross.setColumnWidth("5%");
	        colDiscountGross.setDBColumnName("disc_gross_amount");
	        addColumn(colDiscountGross);
	        
	        Column colPatientShare = new Column("Patient Share");//14
	        colPatientShare.setMethodName("getPatient_share_amount");
	        colPatientShare.setColumnWidth("5%");
	        colPatientShare.setDBColumnName("PATIENT_SHARE");
	        addColumn(colPatientShare);*/
	        
	        Column colRejAmt = new Column("Rej.Amt");//15
	        colRejAmt.setMethodName("getRej_amount");
	        colRejAmt.setColumnWidth("5%");
	        colRejAmt.setDBColumnName("rej_amount");
	        addColumn(colRejAmt);
	        
	        
	       
	        
	        Column colPayable = new Column("Approved Amount");//16
	        colPayable.setMethodName("getPayable");
	        colPayable.setColumnWidth("5%");
	        colPayable.setDBColumnName("payable");
	        addColumn(colPayable);
	        
	        
	        Column colRejectionReason = new Column("Rejection Reason");//7
	        colRejectionReason.setMethodName("getRej_reason");
	        colRejectionReason.setColumnWidth("10%");
	        colRejectionReason.setDBColumnName("rej_reason");
	        addColumn(colRejectionReason);
	        
		}

	}
	

