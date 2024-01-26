package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ProviderListNewTable extends Table{

	String flag;
	
	
	public ProviderListNewTable(String flag)
	{
		this.flag=flag;
		
	}
	
	
	
	
	@Override
	public void setTableProperties() {
		
		
		setRowCount(50);
        setCurrentPage(1);
        setPageLinkCount(10);
        if(this.flag.equals("ProviderListNewTable"))
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

        if(this.flag.equals("ProviderListNewTable"))
        {
        	Column colDiagnosisType = new Column("Provider Name");
        	colDiagnosisType.setMethodName("getProviderName");
        	colDiagnosisType.setColumnWidth("15%");
        	colDiagnosisType.setDBColumnName("HOSP_NAME");
        	addColumn(colDiagnosisType);
        }
        
        
        
        
        if(this.flag.equals("ProviderListNewTable"))
        {
        	Column colDiagnosisType = new Column("Governorate/State");
        	colDiagnosisType.setMethodName("getEmirate");
        	colDiagnosisType.setColumnWidth("15%");
        	colDiagnosisType.setDBColumnName("state_name");
        	addColumn(colDiagnosisType);
        }
        
        
        
        
        if(this.flag.equals("ProviderListNewTable"))
        {
        	Column colDiagnosisType = new Column("Area");
        	colDiagnosisType.setMethodName("getArea");
        	colDiagnosisType.setColumnWidth("15%");
        	colDiagnosisType.setDBColumnName("city_name");
        	addColumn(colDiagnosisType);
        }
        
        
        
        
        if(this.flag.equals("ProviderListNewTable"))
        {
        	Column colDiagnosisType = new Column("Phone No.");
        	colDiagnosisType.setMethodName("getProviderPhone");
        	colDiagnosisType.setColumnWidth("15%");
        	colDiagnosisType.setDBColumnName("off_phone_no_1");
        	addColumn(colDiagnosisType);
        }
        
        
        
        
	}

	
}
