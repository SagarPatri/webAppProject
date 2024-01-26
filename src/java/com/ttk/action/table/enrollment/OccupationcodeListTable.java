package com.ttk.action.table.enrollment;
import com.ttk.action.table.Column;
import com.ttk.action.table.Table;
public class OccupationcodeListTable extends Table{
	@Override
	public void setTableProperties(){
		setRowCount(1000);
		setCurrentPage(1);
		setPageLinkCount(10);
		Column colOccupationCodes=new Column("Occupation Codes");
		colOccupationCodes.setMethodName("getOccupationCode");
		colOccupationCodes.setColumnWidth("5%");
		//colOccupationCodes.setIsLink(true);
		colOccupationCodes.setDBColumnName("OCCUPATION_CODES");
		addColumn(colOccupationCodes);
		
		Column colOccupCodeDesc=new Column("Occupation Code Description");
		colOccupCodeDesc.setMethodName("getOccuCodeDesc");
		colOccupCodeDesc.setColumnWidth("15%");
		colOccupCodeDesc.setIsLink(true);
		colOccupCodeDesc.setDBColumnName("OCCUPATION_CODE_DESCRIPTION");
		addColumn(colOccupCodeDesc);

	}


}
