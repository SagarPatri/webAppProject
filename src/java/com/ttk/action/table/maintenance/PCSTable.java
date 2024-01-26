/**
 * @ (#) PCSTable.java
 * Project      : TTK HealthCare Services
 * File         : PCSTable.java
 * Author       : Balaji C R B
 * Company      : Span Systems Corporation
 * Date Created : August 22,2008
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.maintenance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class PCSTable extends Table{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1446863102426562560L;

	@Override
	public void setTableProperties() {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for PCS Code
        Column colPCSCode = new Column("Procedure Code");
        colPCSCode.setMethodName("getProcedureCode");
        colPCSCode.setColumnWidth("30%");
        colPCSCode.setIsLink(true);
        colPCSCode.setIsHeaderLink(true);
        colPCSCode.setHeaderLinkTitle("Sort by: Procedure Code");
        colPCSCode.setDBColumnName("PROC_CODE");
        addColumn(colPCSCode);

        //Setting properties for PCS Description.
        Column colPCSName = new Column("Procedure Description");
        colPCSName.setMethodName("getProcedureDescription");
        colPCSName.setColumnWidth("70%");
        colPCSName.setIsHeaderLink(true);
        colPCSName.setHeaderLinkTitle("Sort by: Procedure Description");
        colPCSName.setDBColumnName("PROC_DESCRIPTION");
        addColumn(colPCSName);
	}

}
