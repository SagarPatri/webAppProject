/**
 * @ (#) ChequeSeriesTable.java June 9th, 2006
 * Project      : TTK HealthCare Services
 * File         : ChequeSeriesTable.java
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

public class ChequeSeriesTable extends Table
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
        Column colFromDate=new Column("Starting Number");
        colFromDate.setMethodName("getStartNo");
        colFromDate.setColumnWidth("50%");
        colFromDate.setIsLink(true);
        colFromDate.setIsHeaderLink(true);
        colFromDate.setHeaderLinkTitle("Sort by: Starting Number");
        colFromDate.setDBColumnName("CHK_START_NUM");
        addColumn(colFromDate);
        
        //Setting properties for Ending Number
        Column colToDate = new Column("Ending Number");
        colToDate.setMethodName("getEndNo");
        colToDate.setColumnWidth("50%");
        colToDate.setIsHeaderLink(true);
        colToDate.setHeaderLinkTitle("Sort by: Ending Number");
        colToDate.setDBColumnName("CHK_END_NUM");
        addColumn(colToDate);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
    }//end of setTableProperties()
    
}//end of ChequeSeriesTable class

