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

public class MonthlyRemitTable extends Table {

	/** long serialVersionUID. */
	private static final long serialVersionUID = -2689842754056965460L;

	/**
     * This creates the column properties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //Setting properties for Product Name
        Column colInsuranceCompany = new Column("Insurance Company");
        colInsuranceCompany.setMethodName("getInsCompanyName");
        colInsuranceCompany.setColumnWidth("45%");
        colInsuranceCompany.setIsHeaderLink(true);
        colInsuranceCompany.setHeaderLinkTitle("Sort by: Insurance Company");
        colInsuranceCompany.setDBColumnName("INS_COMP_NAME");
        addColumn(colInsuranceCompany);
        
        //Setting properties for Insurance company name
        Column colYear = new Column("Year");
        colYear.setMethodName("getYear");
        colYear.setColumnWidth("12%");
        colYear.setIsHeaderLink(true);
        colYear.setHeaderLinkTitle("Sort by: Year");
        colYear.setDBColumnName("monthly_remitt_year");
        addColumn(colYear);        
        
        //Setting properties for Status
        Column colMonth =new Column("Month");
        colMonth.setMethodName("getMonth");
        colMonth.setColumnWidth("13%");
        colMonth.setIsHeaderLink(true);
        colMonth.setHeaderLinkTitle("Sort by: Month");
        colMonth.setDBColumnName("monthly_remitt_month");    
        addColumn(colMonth);
        
        //Setting properties for Status
        Column colChequeDate =new Column("Challan Ref. No.");
        colChequeDate.setMethodName("getChallanrefNbr");
        colChequeDate.setColumnWidth("25%");
        colChequeDate.setIsHeaderLink(true);
        colChequeDate.setHeaderLinkTitle("Sort by: Challan Ref. No.");
        colChequeDate.setDBColumnName("CHALLAN_REF_NUMBER");    
        addColumn(colChequeDate);
        
        //Setting properties for image  
        Column colImage2 = new Column("");
        colImage2.setIsImage(true);
        colImage2.setIsImageLink(true);
        colImage2.setImageName("getMonthlyRemitImageName");
        colImage2.setImageTitle("getMonthlyRemitImageTitle");
        addColumn(colImage2);
        
        
    }//end of setTableProperties()
}//end of MonthlyRemitTable
