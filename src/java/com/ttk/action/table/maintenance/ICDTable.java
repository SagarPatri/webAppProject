/**
 * @ (#) ICDTable.java May 14, 2008
 * Project      : TTK HealthCare Services
 * File         : ICDTable.java
 * Author       : UNNI V MANA
 * Company      : Span Systems Corporation
 * Date Created : May 14, 2008
 *
 * @author       : UNNI V MANA
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.maintenance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ICDTable extends Table{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1446863102426562560L;

	@Override
	public void setTableProperties() {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for ICD Code
        Column colICDCode = new Column("Code");
        colICDCode.setMethodName("getICDCode");
        colICDCode.setColumnWidth("30%");
        colICDCode.setIsLink(true);
        colICDCode.setIsHeaderLink(true);
        colICDCode.setDBColumnName("ICD_CODE");
        addColumn(colICDCode);

        //Setting properties for ICD Description.
        Column colICDName = new Column("Description");
        colICDName.setMethodName("getDescription");
        colICDName.setColumnWidth("70%");
        colICDName.setIsHeaderLink(true);
        colICDName.setDBColumnName("PED_DESCRIPTION");
        addColumn(colICDName);
	}

}
