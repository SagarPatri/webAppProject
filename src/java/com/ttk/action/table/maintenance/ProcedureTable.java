/**
 * @ (#) ProcedureTable.java 
 * Project       : TTK HealthCare Services
 * File          : ProcedureTable.java
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : Oct 22, 2007
 *
 * @author       : Balaji C R B
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.maintenance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of Procedure Code and Procedure Name
 */
public class ProcedureTable extends Table // implements TableObjectInterface,Serializable 
{
	/**
	 * This creates the columnproperties objects for each and 
	 * every column and adds the column object to the table
	 */
	
	public void setTableProperties()
	{
		setRowCount(10);
		setCurrentPage(1);
		setPageLinkCount(10);
		//Setting properties for Procedure Code
		Column colProcedureCode=new Column("Procedure Code");
		colProcedureCode.setMethodName("getProcedureCode"); 
		colProcedureCode.setColumnWidth("20%");		
		colProcedureCode.setIsHeaderLink(true);
		colProcedureCode.setHeaderLinkTitle("Sort by: Procedure Code");
		colProcedureCode.setDBColumnName("PROC_CODE");
		addColumn(colProcedureCode);		
				
		//Setting properties for Procedure Name
		Column colProcedureDesc=new Column("Procedure Description");
		colProcedureDesc.setMethodName("getProcedureDescription");
		colProcedureDesc.setColumnWidth("80%");
		colProcedureDesc.setIsHeaderLink(true);
		colProcedureDesc.setHeaderLinkTitle("Sort by: Procedure Description");
		colProcedureDesc.setDBColumnName("PROC_DESCRIPTION");
		addColumn(colProcedureDesc);
		
		//Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        colSelect.setWidth(10);
        addColumn(colSelect);
				
	}
}
