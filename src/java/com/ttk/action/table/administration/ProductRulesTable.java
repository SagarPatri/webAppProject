package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ProductRulesTable extends Table{

	public void setTableProperties()
	{
		setRowCount(50);
		setCurrentPage(1);
		setPageLinkCount(10);

		//Creating From Date object for Revision column
		Column columnFromDate =new Column("From Date ");
		columnFromDate.setMethodName("getStrFromDate");
		columnFromDate.setColumnWidth("50%");
		columnFromDate.setIsLink(true);
		addColumn(columnFromDate);

		// Creating To Date object for Revision column
		Column columnToDate =new Column("To Date ");
		columnToDate.setMethodName("getStrEndDate");
		columnToDate.setColumnWidth("50%");
		addColumn(columnToDate);
	}//end of public void setTableProperties()
}//end of ProductRulesTable.java
