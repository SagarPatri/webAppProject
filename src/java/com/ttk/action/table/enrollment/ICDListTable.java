/**
 * @ (#) ICDListTable.java Nov 27, 2006
 * Project      : TTK HealthCare Services
 * File         : ICDListTable.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Nov 27, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of ICD table
 */
public class ICDListTable extends Table {

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
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
        colICDName.setColumnWidth("50%");
        colICDName.setIsHeaderLink(true);
        colICDName.setDBColumnName("PED_DESCRIPTION");
        addColumn(colICDName);

        //Setting properties for Status in rule
        Column colStatusInRule=new Column("Rule Association");
        colStatusInRule.setMethodName("getRuleAssociateYN");
        colStatusInRule.setColumnWidth("20%");
        addColumn(colStatusInRule);
    }// end of public void setTableProperties()

}
