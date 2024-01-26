/**
 * @ (#) HospitalTariffPlanTable.javaOct 22, 2005
 * Project      : TTK HealthCare Services
 * File         : HospitalTariffPlanTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Oct 22, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class HospitalTariffPlanTable extends Table
{
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(20);
        
        //Setting properties for plan name
        Column colName = new Column(" Name");
        colName.setMethodName("getTariffPlanName");
        colName.setColumnWidth("40%");
        colName.setIsHeaderLink(true);
        colName.setIsLink(true);
        colName.setHeaderLinkTitle("Sort by Plan Name");
        colName.setDBColumnName("PLAN_NAME");
        addColumn(colName);
        
//      Setting properties for plan description
        Column colDescription = new Column("Description");
        colDescription.setMethodName("getTariffPlanDesc");
        colDescription.setColumnWidth("60%");
        colDescription.setIsHeaderLink(true);
        colDescription.setHeaderLinkTitle("Sort by Description");
        colDescription.setDBColumnName("PLAN_DESCRIPTION");
        addColumn(colDescription);       
    }//end of public void setTableProperties()    


}
