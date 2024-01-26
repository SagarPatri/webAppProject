package com.ttk.action.table;

public class ProductListVatTable extends Table {

	
	public void setTableProperties() {


		

		 
	     setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);  
	
       
       //1 Currency Code
       Column colCurrencyCode = new Column("Product Name");
       colCurrencyCode.setMethodName("getProductName");
       colCurrencyCode.setColumnWidth("18%");
       //colCurrencyCode.setIsLink(true);
       colCurrencyCode.setDBColumnName("Product_Name");
       addColumn(colCurrencyCode); 
       
       //2 Currency Name
       Column colCurrencyName = new Column("Product Code");
       colCurrencyName.setMethodName("getProductCode");
       colCurrencyName.setColumnWidth("15%");
       colCurrencyName.setDBColumnName("ins_product_code");
       addColumn(colCurrencyName);
       
       //3 Units per AED
       Column colUnitsperAED = new Column("Product Type");
       colUnitsperAED.setMethodName("getProductType");
       colUnitsperAED.setColumnWidth("15%");
       colUnitsperAED.setDBColumnName("Product_type");
       addColumn(colUnitsperAED);
       
       //4 AED per Unit
       Column colAEDperUnit = new Column("Network");
       colAEDperUnit.setMethodName("getNetworkType");
       colAEDperUnit.setColumnWidth("17%");
       colAEDperUnit.setDBColumnName("net_work");
       addColumn(colAEDperUnit);
       
       //5 Conversion Date
       Column colConversionDate = new Column("Description");
       colConversionDate.setMethodName("getDescription");
       colConversionDate.setColumnWidth("20%");
       colConversionDate.setDBColumnName("description");
       addColumn(colConversionDate);
       
       //6 Uploaded Date
       Column colUploadedDate = new Column("VAT%");
       colUploadedDate.setMethodName("getVatPercent");
       colUploadedDate.setColumnWidth("15%");
       colUploadedDate.setDBColumnName("vat_percent");
       addColumn(colUploadedDate);
       
       //7 Uploaded Date
       Column colSelect = new Column("Select");
		colSelect.setComponentType("checkbox");
		colSelect.setComponentName("chkopt");		
		addColumn(colSelect); 	
		
		
		

	}

}
