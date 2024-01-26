/**
 * @ (#) CoverageTable.java Aug 25th, 2008
 * Project      : TTK HealthCare Services
 * File         : CoverageTable.java
 * Author       : Sendhil Kumar V
 * Company      : Span Systems Corporation
 * Date Created : Aug 25th, 2008
 *
 * @author       : Sendhil Kumar V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class CoverageTable extends Table
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
        Column colClauseNo=new Column("Coverage Number");
        colClauseNo.setMethodName("getClauseNbr");
        colClauseNo.setColumnWidth("20%");
        colClauseNo.setIsLink(true);
       // colClauseNo.setIsHeaderLink(true);
        colClauseNo.setHeaderLinkTitle("Clause Number");
        colClauseNo.setDBColumnName("CLAUSE_NUMBER");
        addColumn(colClauseNo);
        
        //Setting properties for Ending Number
        Column colDetails = new Column("Coverage Description");
        colDetails.setMethodName("getClauseDesc");
        colDetails.setColumnWidth("90%");
        //colDetails.setIsHeaderLink(true);
        colDetails.setHeaderLinkTitle("Clause Description");
        colDetails.setDBColumnName("CLAUSE_DESCRIPTION");
        addColumn(colDetails);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
    }//end of setTableProperties()
}//end of CoverageTable