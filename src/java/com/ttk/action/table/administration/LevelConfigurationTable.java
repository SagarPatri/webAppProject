
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

public class LevelConfigurationTable extends Table
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
        Column colBufferType=new Column("BufferType");
        colBufferType.setMethodName("getGeneralType");
        colBufferType.setColumnWidth("20%");
        colBufferType.setIsLink(true);
        colBufferType.setIsHeaderLink(true);
        colBufferType.setHeaderLinkTitle("Buffer Type");
        colBufferType.setDBColumnName("BUFFER_TYPE");
        addColumn(colBufferType);
        
        //Setting properties for Starting Number
        Column colClauseNo=new Column("Level Names");
        colClauseNo.setMethodName("getLevelDesc");
        colClauseNo.setColumnWidth("20%");
       // colClauseNo.setIsLink(true);
        colClauseNo.setIsHeaderLink(true);
        colClauseNo.setHeaderLinkTitle("Level Names");
        colClauseNo.setDBColumnName("LEVEL_DESC");
        addColumn(colClauseNo);
        
        //Setting properties for Ending Number
        Column colDetails = new Column("Limit Values");
        colDetails.setMethodName("getLevelsLimit");
        colDetails.setColumnWidth("90%");
        //colDetails.setIsHeaderLink(true);
        colDetails.setHeaderLinkTitle("Limit Values");
        colDetails.setDBColumnName("LEVEL_LIMIT");
        addColumn(colDetails);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
    }//end of setTableProperties()
}//end of LevelConfigurationTable
