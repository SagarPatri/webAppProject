/**
 * @ (#) TDSAttrRevisionTable.java July 31st, 2009
 * Project      : TTK HealthCare Services
 * File         : TDSAttrRevisionTable.java
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : July 31st, 2009
 *
 * @author       :  Balakrishna Erram
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class TDSAttrRevisionTable extends Table
{

//	private static final long serialVersionUID = 1L;

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
        Column colFromDate=new Column("Valid From");
        colFromDate.setMethodName("getTdsRevDateFrom");
        colFromDate.setColumnWidth("50%");
        colFromDate.setIsLink(true);
        colFromDate.setHeaderLinkTitle("Sort by: Valid From");
        colFromDate.setDBColumnName("REV_DATE_FROM");
        addColumn(colFromDate);
        
        //Setting properties for Ending Number
        Column colToDate = new Column("Valid To");
        colToDate.setMethodName("getTdsRevDateTo");
        colToDate.setColumnWidth("50%");
        colToDate.setHeaderLinkTitle("Sort by: Valid To");
        colToDate.setDBColumnName("REV_DATE_TO");
        addColumn(colToDate);
        
    }//end of setTableProperties()
    
}//end of ChequeSeriesTable class

