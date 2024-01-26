/**
 * @ (#) HospitalValidationTable.java Sep 20, 2005
 * Project      : TTK HealthCare Services
 * File         : HospitalValidationTable.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Sep 20, 2005
 *
 * @author       :  Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of Hospital validation Details table
 * 
 */
public class HospitalValidationTable extends Table {
    
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(20);
        
        //Setting properties for Marked Date
        Column colMarkedDate = new Column("Marked Date");
        colMarkedDate.setMethodName("getMarkedDate");
        //colMarkedDate.setWidth(150);
        colMarkedDate.setColumnWidth("15%");
        colMarkedDate.setIsLink(true);
        colMarkedDate.setLinkTitle("Edit");
        colMarkedDate.setDBColumnName("MARKED_DATE");
        addColumn(colMarkedDate);
        
        //Setting properties for Validated By
        Column colValidatedBy = new Column("Validated By");
        colValidatedBy.setMethodName("getValidatedBy");
        colValidatedBy.setColumnWidth("20%");
        colValidatedBy.setDBColumnName("VALIDATED_BY");
        addColumn(colValidatedBy);
        
        //Setting properties for Validated Date
        Column colValidatedDate = new Column("Validated Date");
        colValidatedDate.setMethodName("getValidatedDate");
        //colValidatedDate.setWidth(200);
        colValidatedDate.setColumnWidth("20%");
        colValidatedDate.setDBColumnName("VALIDATED_DATE");
        addColumn(colValidatedDate);
        
        //Setting properties for Description
        Column colDescription = new Column("Description");
        colDescription.setMethodName("getRemarks");
        colDescription.setColumnWidth("45%");
        colDescription.setDBColumnName("REMARKS");
        addColumn(colDescription);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        colSelect.setWidth(10);
        addColumn(colSelect);
    }//end of public void setTableProperties()
    
}// end of HospitalValidationTable
