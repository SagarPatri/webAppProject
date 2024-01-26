/**
 * @ (#) HospitalTariffRevisePlanORTTable.java Nov 11, 2005
 * Project      : TTK HealthCare Services
 * File         : HospitalTariffRevisePlanORTTable.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Nov 11, 2005
 *
 * @author       :  Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */


package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/*
 * HopitalTariffREvisePlanTable class provides the information regarding 
 * hospital tariff revise plan
 */
public class HospitalTariffRevisePlanORTTable extends Table {

    /**
     * This method,setTableProperties(), sets the columnproperties objects for each and 
     * every column and adds the column object to the table for NHCP Offered Rates revision
     */
    public void setTableProperties() {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(20);
        Column columnRevision =new Column("Revision");           // Creating Column object for Revision column 
        columnRevision.setMethodName("getRevisionName");                        // Set the method name    
        columnRevision.setColumnWidth("30%");                    // Set the column width to be displayed in Grid
        columnRevision.setIsLink(true);                          // Is this column data contains  the hyperlink
        columnRevision.setLinkTitle("Revision");                 // Set the link title
        columnRevision.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
        columnRevision.setHeaderLinkTitle("Sort by: Revision");  // Set the header link title
        columnRevision.setDBColumnName("REVISION_NO");                      // Set the Database column name for this column 
        addColumn(columnRevision);                               // Add the column to the table
         
        Column columnPlanName =new Column("Plan Name");          // Creating Column object for Revision column 
        columnPlanName.setMethodName("getTariffPlanName");                        // Set the method name    
        columnPlanName.setColumnWidth("40%");                    // Set the column width to be displayed in Grid
        columnPlanName.setLinkParamName("SecondLink");
        //columnPlanName.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
        //columnPlanName.setHeaderLinkTitle("Sort by: Plan Name"); // Set the header link title
        columnPlanName.setDBColumnName("PLAN_NAME");                      // Set the Database column name for this column 
        addColumn(columnPlanName);                               // Add the column to the table
        
        Column columnStartDate =new Column("Start Date");          // Creating Column object for Revision column 
        columnStartDate.setMethodName("getStartDate");                        // Set the method name    
        columnStartDate.setColumnWidth("15%");                    // Set the column width to be displayed in Grid
        columnStartDate.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
        columnStartDate.setHeaderLinkTitle("Sort by: Start Date"); // Set the header link title
        columnStartDate.setDBColumnName("FROM_DATE");                      // Set the Database column name for this column 
        addColumn(columnStartDate);                               // Add the column to the table

        Column columnEndDate =new Column("End Date");           // Creating Column object for Revision column 
        columnEndDate.setMethodName("getEndDate");                        // Set the method name    
        columnEndDate.setColumnWidth("15%");                    // Set the column width to be displayed in Grid
        columnEndDate.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
        columnEndDate.setHeaderLinkTitle("Sort by: End Date");  // Set the header link title
        columnEndDate.setDBColumnName("TO_DATE");                      // Set the Database column name for this column 
        addColumn(columnEndDate);                               // Add the column to the table

        


    }

}
