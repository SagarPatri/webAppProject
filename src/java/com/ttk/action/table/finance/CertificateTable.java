/**
 * @ (#) CertificateTable.java June 9th, 2006
 * Project      : TTK HealthCare Services
 * File         : CertificateTable.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : June 9th, 2006
 *
 * @author       :  Krupa J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class CertificateTable extends Table
{

	private static final long serialVersionUID = 1L;

	/**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
    	setRowCount(200);
        setCurrentPage(1);
        setPageLinkCount(50);

        //Setting properties for Starting Number
        Column colFromDate=new Column("Financial Year From");
        colFromDate.setMethodName("getFinanceYear");
        colFromDate.setColumnWidth("30%");
//        colFromDate.setIsLink(false);
        colFromDate.setIsHeaderLink(true);
        colFromDate.setHeaderLinkTitle("Sort by: Financial Year From");
        colFromDate.setDBColumnName("FINANCIAL_YEAR");
        addColumn(colFromDate);
        
        //Setting Properties for Frequency
        Column colQuarter = new Column("Frequency");
        colQuarter.setMethodName("getTdsBatchQtrDesc");
        colQuarter.setColumnWidth("30%");
        colQuarter.setIsHeaderLink(true);
        colQuarter.setHeaderLinkTitle("Sort by: Frequency");
        colQuarter.setDBColumnName("QUARTER_DESC");
        addColumn(colQuarter);
        
        //Setting properties for Ending Number
        Column colToDate = new Column("Document Generated");
        colToDate.setMethodName("getProcessedYN");
        colToDate.setColumnWidth("30%");
        colToDate.setHeaderLinkTitle("Sort by: Document Generated");
        colToDate.setDBColumnName("DOC_GENERATED_YN");
        addColumn(colToDate);
        
      //Setting properties for image  
        Column colImage = new Column("");
        colImage.setIsImage(true);
        colImage.setIsImageLink(true);
        colImage.setImageName("getRegenerationImageName");
        colImage.setImageTitle("getRegenerationImageTitle");
        addColumn(colImage);
    }//end of setTableProperties()
    
}//end of ChequeSeriesTable class

