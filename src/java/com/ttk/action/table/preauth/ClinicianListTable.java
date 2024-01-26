package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ClinicianListTable extends Table{

	
	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	
	@Override
	public void setTableProperties() {

		setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Diagnosis
        Column colClinicianId = new Column("Clinician Id");
        colClinicianId.setMethodName("getClinicianId");
        colClinicianId.setColumnWidth("15%");
        colClinicianId.setIsLink(true);
        colClinicianId.setDBColumnName("PROFESSIONAL_ID");
        addColumn(colClinicianId);

        //Setting properties for Clinician Name
        Column colClinicianName = new Column("Clinician Name");
        colClinicianName.setMethodName("getClinicianName");
        colClinicianName.setColumnWidth("15%");
        colClinicianName.setDBColumnName("CONTACT_NAME");
        addColumn(colClinicianName);
	}

}
