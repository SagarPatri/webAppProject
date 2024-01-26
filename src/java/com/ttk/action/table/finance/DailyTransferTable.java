/**
 * @ (#) DailyTransferTable.java August 6, 2009
 * Project      : TTK HealthCare Services
 * File         : DailyTransferTable.java
 * Author       : Navin Kumar R
 * Company      : Span Systems Corporation
 * Date Created : August 6, 2009
 *
 * @author       :  
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Daily Transfer
 */
public class DailyTransferTable extends Table {

	/** long serialVersionUID. */
	private static final long serialVersionUID = 6627172072454843174L;

	/**
     * This creates the column properties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(50);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //Setting properties for Product Name
        Column colClaimSettleNo = new Column("Claim Settlement No.");
        colClaimSettleNo.setMethodName("getClaimSettleNo");
        colClaimSettleNo.setColumnWidth("20%");
        colClaimSettleNo.setIsHeaderLink(true);
        colClaimSettleNo.setHeaderLinkTitle("Sort by: Claim Settlement No.");
        colClaimSettleNo.setDBColumnName("CLAIM_SETTLEMENT_NUMBER");
        addColumn(colClaimSettleNo);
        
        //Setting properties for Insurance company name
        Column colFloatAcctNo = new Column("Float No.");
        colFloatAcctNo.setMethodName("getFloatAccountNo");
        colFloatAcctNo.setColumnWidth("15%");
        colFloatAcctNo.setIsHeaderLink(true);
        colFloatAcctNo.setHeaderLinkTitle("Sort by: Float No.");
        colFloatAcctNo.setDBColumnName("FLOAT_ACCT_NUMBER");
        addColumn(colFloatAcctNo);        
        
        //Setting properties for Status
        Column colChequeNo =new Column("Cheque No.");
        colChequeNo.setMethodName("getChequeNo");
        colChequeNo.setColumnWidth("12%");
        colChequeNo.setIsHeaderLink(true);
        colChequeNo.setHeaderLinkTitle("Sort by: Cheque No");
        colChequeNo.setDBColumnName("CHEQUE_NUMBER");    
        addColumn(colChequeNo);
        
        //Setting properties for Status
        Column colChequeDate =new Column("TDS Deducted Date");
        colChequeDate.setMethodName("getChequeDate");
        colChequeDate.setColumnWidth("12%");
        colChequeDate.setIsHeaderLink(true);
        colChequeDate.setHeaderLinkTitle("Sort by: Generated Date");
        colChequeDate.setDBColumnName("GENERATED_DATE");    
        addColumn(colChequeDate);
        
        //Setting properties for Status
        Column colInsuranceCompany =new Column("Insurance Company");
        colInsuranceCompany.setMethodName("getInsCompanyName");
        colInsuranceCompany.setColumnWidth("24%");
        colInsuranceCompany.setIsHeaderLink(true);
        colInsuranceCompany.setHeaderLinkTitle("Sort by: Insurance Company");
        colInsuranceCompany.setDBColumnName("INS_COMP_NAME");    
        addColumn(colInsuranceCompany);
        
        //Setting properties for Status
        Column colDailyTransferDate =new Column("Daily Transfer Date");
        colDailyTransferDate.setMethodName("getDailyTransDate");
        colDailyTransferDate.setColumnWidth("12%");
        colDailyTransferDate.setIsHeaderLink(true);
        colDailyTransferDate.setHeaderLinkTitle("Sort by: Daily Transfer Date");
        colDailyTransferDate.setDBColumnName("DAILY_REMIT_DATE");    
        addColumn(colDailyTransferDate);
               
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");       
        addColumn(colSelect); 
    }//end of setTableProperties()
}//end of DailyTransferTable
