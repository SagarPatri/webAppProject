
package com.ttk.action.table.onlineforms.insuranceLogin;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class CorporateDataTable extends Table{

	
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
        Column colCorporateName = new Column("Corporate Name");
        colCorporateName.setMethodName("getCorpName");
        colCorporateName.setColumnWidth("50%");
        colCorporateName.setIsLink(true);
        colCorporateName.setDBColumnName("GROUP_NAME");
        addColumn(colCorporateName);

        //Setting properties for Member Name
        Column colMemberName = new Column("Policy Number");
        colMemberName.setMethodName("getPolicyNumb");
        colMemberName.setColumnWidth("25%");
        colMemberName.setDBColumnName("POLICY_NUMBER");
        addColumn(colMemberName);
        
        //Setting properties for Member Name
        Column colEmployeeName = new Column("Effective To Date");
        colEmployeeName.setMethodName("getEffectiveToDate");
        colEmployeeName.setColumnWidth("25%");
        colEmployeeName.setDBColumnName("EFFECTIVE_TO_DATE");
        addColumn(colEmployeeName);
        
	}

}
