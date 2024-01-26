/**
 * @ (#) ServTaxConfTable.java Sep 16, 2010
 * Project       : TTK HealthCare Services
 * File          : ServTaxConfTable.java
 * Author        : Manikanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : Sep 16, 2010
 *
 * @author       : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ServTaxConfTable extends Table {
	
	/**
     * This creates the column properties objects for each and 
     * every column and adds the column object to the table
     */
	public void setTableProperties()
    {
    	setRowCount(100);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //Setting properties for Start Date
        Column colFromDate =new Column("Valid From");
        colFromDate.setMethodName("getServRevDateFrom");
        colFromDate.setColumnWidth("50%");
        colFromDate.setIsLink(true);
        colFromDate.setHeaderLinkTitle("Sort by: Valid From");
        colFromDate.setDBColumnName("REV_DATE_FROM");
        addColumn(colFromDate);
        
        //Setting properties for End Date
        Column colToDate = new Column("Valid To");
        colToDate.setMethodName("getServRevDateTo");
        colToDate.setColumnWidth("50%");
        colToDate.setHeaderLinkTitle("Sort by: Valid To");
        colToDate.setDBColumnName("REV_DATE_TO");
        addColumn(colToDate);
        
    }//end of setTableProperties()
	
}//end of ServTaxConfTable
