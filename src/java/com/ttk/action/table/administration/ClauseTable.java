/**
 * @ (#) ClauseTable.java 9th July 2007
 * Project      : TTK HealthCare Services
 * File         : ClauseTable.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 9th July 2007
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ClauseTable extends Table
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
        Column colClauseNo=new Column("Clause Number");
        colClauseNo.setMethodName("getClauseNbr");
        colClauseNo.setColumnWidth("20%");
        colClauseNo.setIsLink(true);
       // colClauseNo.setIsHeaderLink(true);
        colClauseNo.setHeaderLinkTitle("Clause Number");
        colClauseNo.setDBColumnName("CLAUSE_NUMBER");
        addColumn(colClauseNo);
        
        //Setting properties for Ending Number
        Column colDetails = new Column("Clause Description");
        colDetails.setMethodName("getClauseDesc");
       colDetails.setColumnWidth("50%");
        //colDetails.setIsHeaderLink(true);
        colDetails.setHeaderLinkTitle("Clause Description");
        colDetails.setDBColumnName("CLAUSE_DESCRIPTION");
        addColumn(colDetails);
        
		//Added as per KOC 1179 Short fall Cr
        Column colClauseType=new Column("Clause Type");
        colClauseType.setMethodName("getClauseForDesc");
        colClauseType.setColumnWidth("20%");
        colClauseType.setIsLink(true);
       // colClauseNo.setIsHeaderLink(true);
        colClauseType.setHeaderLinkTitle("Clause Type");
        colClauseType.setDBColumnName("CLAUSE_TYPE");
        addColumn(colClauseType);
        
        //Added as per KOC 1179 Short fall Cr
        //Setting properties for image  
        Column colImage2 = new Column("");
        colImage2.setIsImage(true);
        colImage2.setIsImageLink(true);
        colImage2.setImageName("getClauseImageName");
        colImage2.setImageTitle("getClauseImageTitle");
        addColumn(colImage2);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
    }//end of setTableProperties()
}//end of ClauseTable()
