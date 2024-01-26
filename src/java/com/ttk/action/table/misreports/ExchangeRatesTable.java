package com.ttk.action.table.misreports;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ExchangeRatesTable extends Table{

	public void setTableProperties() {
		 
		     setRowCount(10);
	         setCurrentPage(1);
	         setPageLinkCount(10);  
		
	        
	        //1 Currency Code
	        Column colCurrencyCode = new Column("Currency Code");
	        colCurrencyCode.setMethodName("getCurrencyCode");
	        colCurrencyCode.setColumnWidth("16%");
	        //colCurrencyCode.setIsLink(true);
	        colCurrencyCode.setDBColumnName("currency_code");
	        addColumn(colCurrencyCode); 
	        
	        //2 Currency Name
	        Column colCurrencyName = new Column("Currency Name");
	        colCurrencyName.setMethodName("getCurrencyName");
	        colCurrencyName.setColumnWidth("16%");
	        colCurrencyName.setDBColumnName("currency_name");
	        addColumn(colCurrencyName);
	        
	        //3 Units per AED
	        Column colUnitsperAED = new Column("Units per OMR");
	        colUnitsperAED.setMethodName("getUnitsperAED");
	        colUnitsperAED.setColumnWidth("17%");
	        colUnitsperAED.setDBColumnName("units_per_aed");
	        addColumn(colUnitsperAED);
	        
	        //4 AED per Unit
	        Column colAEDperUnit = new Column("OMR per Unit");
	        colAEDperUnit.setMethodName("getaEDperUnit");
	        colAEDperUnit.setColumnWidth("17%");
	        colAEDperUnit.setDBColumnName("aed_per_unit");
	        addColumn(colAEDperUnit);
	        
	        //5 Conversion Date
	        Column colConversionDate = new Column("Conversion Date");
	        colConversionDate.setMethodName("getConversionDate");
	        colConversionDate.setColumnWidth("17%");
	        colConversionDate.setDBColumnName("conversion_date");
	        addColumn(colConversionDate);
	        
	        //6 Uploaded Date
	        Column colUploadedDate = new Column("Uploaded Date");
	        colUploadedDate.setMethodName("getUploadedDate");
	        colUploadedDate.setColumnWidth("17%");
	        colUploadedDate.setDBColumnName("uploaded_date");
	        addColumn(colUploadedDate);
	        
	        
	}

}
