/**
 * @ (#) ICDCodeTable.java Feb 10, 2006
 * Project      : TTK HealthCare Services
 * File         : ICDCodeTable.java
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : Feb 10, 2006
 *
 * @author       : Lancy A
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * this class provides the information of ICD table
 */
public class ICDCodeTable extends Table
{
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(100);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //Setting properties for % match.
        Column colMatch = new Column("% Match");
        colMatch.setMethodName("getPercentage");
        colMatch.setColumnWidth("10%");
        colMatch.setIsLink(true);
        addColumn(colMatch);
        
        //Setting properties for DRG code.
        Column colDRGCode = new Column("DRG Code");
        colDRGCode.setMethodName("getDRG");
        colDRGCode.setColumnWidth("10%");
        addColumn(colDRGCode);
        
        //Setting properties for DRG detail.
        Column colDRGDetail = new Column("DRG Detail");
        colDRGDetail.setMethodName("getDRG_D");
        colDRGDetail.setColumnWidth("27%");
        addColumn(colDRGDetail);
        
        //Setting properties for DRG description.
        Column colDRGDescription = new Column("DRG Description");
        colDRGDescription.setMethodName("getDescription");
        colDRGDescription.setColumnWidth("53%");
        addColumn(colDRGDescription);       
    }// end of public void setTableProperties()    
}// end of ICDCodeTable class
