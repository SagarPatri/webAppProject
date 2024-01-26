/**
 * @ (#) AssociateProcedureTable.javaOct 19, 2005
 * Project      : TTK HealthCare Services
 * File         : AssociateProcedureTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Oct 19, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *
 *
 *
 */
public class AssociateProcedureTable extends Table
{
    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for procedure code
        Column colCode = new Column("Procedure Code");
        colCode.setMethodName("getProcedureCode");
        colCode.setColumnWidth("20%");
        colCode.setIsHeaderLink(true);
        colCode.setHeaderLinkTitle("Sort by Procedure Code");
        colCode.setDBColumnName("PROC_CODE");
        addColumn(colCode);

        //Setting properties for procedure name
        Column colName = new Column("Procedure Name");
        colName.setMethodName("getProcedureDescription");
        colName.setColumnWidth("80%");
        colName.setIsHeaderLink(true);
        colName.setHeaderLinkTitle("Sort by Procedure Name");
        colName.setDBColumnName("PROC_DESCRIPTION");
        addColumn(colName);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
    }//end of public void setTableProperties()

}
