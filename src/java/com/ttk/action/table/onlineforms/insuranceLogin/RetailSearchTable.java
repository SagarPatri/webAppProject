package com.ttk.action.table.onlineforms.insuranceLogin;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class RetailSearchTable extends Table{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	
	@Override
	public void setTableProperties() {

		setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for ENROLLMENT ID
        Column colCorporateName = new Column("Product Name");
        colCorporateName.setMethodName("getProductName");
        colCorporateName.setColumnWidth("50%");
        colCorporateName.setIsLink(true);
        colCorporateName.setDBColumnName("PRODUCT_NAME");
        addColumn(colCorporateName);

        
        //Setting properties for Date from
        Column colDateFrom = new Column("Date From");
        colDateFrom.setMethodName("getDateAsString");
        colDateFrom.setColumnWidth("25%");
        colDateFrom.setDBColumnName("VALID_FROM");
        addColumn(colDateFrom);
        
        //Setting properties for Date To
        Column colDateTo = new Column("Date To");
        colDateTo.setMethodName("getDateTo");
        colDateTo.setColumnWidth("25%");
        colDateTo.setDBColumnName("VALID_TO");
        addColumn(colDateTo);
        
	}

}
