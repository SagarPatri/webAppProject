package com.ttk.dto.onlineforms.insuranceLogin;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ProductTable extends Table{

	
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

        //Setting properties for Product Name
        Column colProductName = new Column("Product Name");
        colProductName.setMethodName("getProductName");
        colProductName.setColumnWidth("30%");
        colProductName.setIsLink(true);
        colProductName.setDBColumnName("PRODUCT_NAME");
        addColumn(colProductName);
        
        

      //Setting properties for Product Desc
        Column colProductDesc = new Column("Description");
        colProductDesc.setMethodName("getDescription");
        colProductDesc.setColumnWidth("25%");
        addColumn(colProductDesc);
        
      //Setting properties for Product Code
        Column colProductCode = new Column("Product Code");
        colProductCode.setMethodName("getInsProductCode");
        colProductCode.setColumnWidth("15%");
        addColumn(colProductCode);


      //Setting properties for Product Type
        Column colProductType = new Column("Product Type");
        colProductType.setMethodName("getProductCatTypeId");
        colProductType.setColumnWidth("15%");
        addColumn(colProductType);
        
      //Setting properties for Product Auth
        Column colProductAuth = new Column("Product Authority");
        colProductAuth.setMethodName("getAuthorityType");
        colProductAuth.setColumnWidth("15%");
        addColumn(colProductAuth);
        
	}

}
