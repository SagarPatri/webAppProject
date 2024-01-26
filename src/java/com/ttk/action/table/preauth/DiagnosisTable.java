package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class DiagnosisTable extends Table{

	
	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	
	@Override
	public void setTableProperties() {

		setRowCount(50);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Diagnosis
        Column colDiagnosis = new Column("Diagnosis");
        colDiagnosis.setMethodName("getDiagnosisDesc");
        colDiagnosis.setColumnWidth("15%");
        colDiagnosis.setIsLink(true);
        colDiagnosis.setDBColumnName("diagnosys_name");
        addColumn(colDiagnosis);

        //Setting properties for DiagnosisType
        Column colDiagnosisType = new Column("Diagnosis Type");
        colDiagnosisType.setMethodName("getDiagnosisType");
        colDiagnosisType.setColumnWidth("15%");
        colDiagnosisType.setDBColumnName("diag_type");
        addColumn(colDiagnosisType);
		
		Column colDiagnosisHospType = new Column("Hospital Type");
		colDiagnosisHospType.setMethodName("getDiagHospGenTypeId");
		colDiagnosisHospType.setColumnWidth("15%");
		colDiagnosisHospType.setDBColumnName("hospital_general_type_id");
		addColumn(colDiagnosisHospType);
		
		
		Column colDiagnosisTreatmentPlanType = new Column("Treatement Type");
		colDiagnosisTreatmentPlanType.setMethodName("getDiagTreatmentPlanGenTypeId");
		colDiagnosisTreatmentPlanType.setColumnWidth("15%");
		colDiagnosisTreatmentPlanType.setDBColumnName("trtmnt_plan_general_type_id");
		addColumn(colDiagnosisTreatmentPlanType);
		
		Column colImage = new Column("Delete");
        colImage.setIsImage(true);
        colImage.setIsImageLink(true);
        colImage.setImageName("getDeleteName");
        colImage.setImageTitle("getDeleteTitle");
        addColumn(colImage);
		
	}

}
