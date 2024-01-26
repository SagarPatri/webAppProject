/**
 * @ (#) TariffPlansTable.java Sep 28, 2005
 * Project      : TTK HealthCare Services
 * File         : TariffPlansTable.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Sep 28, 2005
 *
 * @author       :  Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class consists of the information requied to prepare grid table
 * for tariff plan screen
 * 
 */
public class TariffPlanTable extends Table{
    
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //Setting properties for Name
        Column colName = new Column("Name");
        colName.setMethodName("getTariffPlanName");
        colName.setColumnWidth("42%");
        colName.setIsLink(true);
        colName.setLinkTitle("Edit Name");
        colName.setIsHeaderLink(true);
        colName.setHeaderLinkTitle("Sort by: Name");
        colName.setDBColumnName("PLAN_NAME");
        addColumn(colName);
        
        //Setting properties for Description
        Column colDescription = new Column("Description");
        colDescription.setMethodName("getTariffPlanDesc");
        colDescription.setColumnWidth("58%");
        colDescription.setIsHeaderLink(true);
        colDescription.setHeaderLinkTitle("Sort by: Description");
        colDescription.setDBColumnName("PLAN_DESCRIPTION");
        addColumn(colDescription);
        
        //Setting properties for image Revision History 
        Column colImage1 = new Column("");
        colImage1.setIsImage(true);
        colImage1.setIsImageLink(true);
        colImage1.setImageName("getImageName");
        colImage1.setImageTitle("getImageTitle");
        addColumn(colImage1);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        colSelect.setWidth(10);
        addColumn(colSelect);
        
    }//end of setTableProperties()
    
}//end of TariffPlansTable
