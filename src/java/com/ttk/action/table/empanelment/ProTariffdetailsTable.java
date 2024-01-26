package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ProTariffdetailsTable extends Table {

	@Override
	public void setTableProperties() {
	
		setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        Column colSlno = new Column("SLNO.");
        colSlno.setMethodName("getsSlno");
        colSlno.setColumnWidth("10%");
        colSlno.setDBColumnName("SLNO");
        addColumn(colSlno);
        
        Column colCount = new Column("Count");
        colCount.setMethodName("getsCount");
        colCount.setColumnWidth("20%");
        colCount.setDBColumnName("TARIIF_COUNT");
        addColumn(colCount);
        
        Column colUploadedDate = new Column("Uploaded Date");
        colUploadedDate.setMethodName("getsUploadedDate");
        colUploadedDate.setColumnWidth("35%");
       // colEmiratesId.setIsLink(true);
        colUploadedDate.setDBColumnName("UPLOADED_DATE");
        addColumn(colUploadedDate);
        
        Column colUploadedBy = new Column("Uploaded by");
        colUploadedBy.setMethodName("getsUploadedBy");
        colUploadedBy.setColumnWidth("35%");
        colUploadedBy.setDBColumnName("UPLOADED_BY");
        addColumn(colUploadedBy);
    
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");       
        addColumn(colSelect); 
	}

}
