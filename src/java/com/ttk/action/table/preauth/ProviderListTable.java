package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ProviderListTable extends Table{

	String flag;
	
	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	public ProviderListTable(String flag)
	{
		this.flag=flag;
		
	}
	@Override
	public void setTableProperties() {

		setRowCount(50);
        setCurrentPage(1);
        setPageLinkCount(10);
        if(this.flag.equals("ProviderListTable"))
		{
        //Setting properties for Diagnosis
        Column colDiagnosis = new Column("Provider Id");
        colDiagnosis.setMethodName("getProviderId");
        colDiagnosis.setColumnWidth("15%");
        colDiagnosis.setIsLink(true);
        colDiagnosis.setDBColumnName("HOSP_LICENC_NUMB");
        addColumn(colDiagnosis);
		}
        
        //Setting properties for DiagnosisType
        if(this.flag.equals("ReportProviderNameListTable"))
        {
        	Column colDiagnosisType = new Column("Provider Name");
        	colDiagnosisType.setMethodName("getProviderName");
        	colDiagnosisType.setColumnWidth("15%");
        	colDiagnosisType.setIsLink(true);
        	colDiagnosisType.setDBColumnName("HOSP_NAME");
        	addColumn(colDiagnosisType);
        }

        if(this.flag.equals("ProviderListTable"))
        {
        	Column colDiagnosisType = new Column("Provider Name");
        	colDiagnosisType.setMethodName("getProviderName");
        	colDiagnosisType.setColumnWidth("15%");
        	colDiagnosisType.setDBColumnName("HOSP_NAME");
        	addColumn(colDiagnosisType);
        }
	}

}
