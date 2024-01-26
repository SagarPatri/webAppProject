/**
 * @ (#) OfficeListTable.java Dec 9th, 2005
 * Project       : TTK HealthCare Services
 * File          : OfficeListTable.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Dec 9th, 2005
 * @author       : Bhaskar Sandra
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class OfficeListTable extends Table {
	/**
	 * This creates the columnproperties objects for each and 
	 * every column and adds the column object to the table
	 */

	public void setTableProperties()
	{
		setRowCount(10);
		setCurrentPage(1);
		setPageLinkCount(10);
		Column columnOfficeCode =new Column("Company Code ");            
		columnOfficeCode.setMethodName("getOfficeCode");                   
		columnOfficeCode.setColumnWidth("30%");                    
		columnOfficeCode.setIsLink(true);                          
		columnOfficeCode.setLinkTitle("Office Code");              
		columnOfficeCode.setIsHeaderLink(true);                    
		columnOfficeCode.setHeaderLinkTitle("Sort by: Company Code");
		columnOfficeCode.setDBColumnName("INS_COMP_CODE_NUMBER");                      
		addColumn(columnOfficeCode);
		
		Column columnDesc =new Column("Office Type");           
		columnDesc.setMethodName("getOfficeType");                 
		columnDesc.setColumnWidth("30%");              
		columnDesc.setIsHeaderLink(true);                    
		columnDesc.setHeaderLinkTitle("Sort by: Office Type");  
		columnDesc.setDBColumnName("DESCRIPTION");               
		addColumn(columnDesc);
	
	}//end of setTableProperties()
}//end of OfficeListTable
