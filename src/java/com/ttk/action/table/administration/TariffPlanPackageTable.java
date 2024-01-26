/**
 * @ (#) TariffPlanPackageTable.java Oct 21, 2005
 * Project      : TTK HealthCare Services
 * File         : TariffPlanPackageTable.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Oct 21, 2005
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
 *This class consists of the information requied to prepare grid table
 * for Available tariff plan package view details screen 
 * 
 */
public class TariffPlanPackageTable extends Table
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
        
        //Setting properties for Package Name
        Column colName = new Column("Name");
        colName.setMethodName("getName");
        colName.setColumnWidth("53%");
        colName.setIsLink(true);
        colName.setLinkTitle("Manage Rates");
        colName.setIsHeaderLink(true);
        colName.setImageName("getImageName");
        colName.setImageTitle("getImageTitle");
        colName.setHeaderLinkTitle("Sort by: Name");
        colName.setDBColumnName("NAME");
        addColumn(colName);
       
        //Setting properties for Package Type
        Column colDescription = new Column("Type");
        colDescription.setMethodName("getType");
        colDescription.setColumnWidth("47%");
        colDescription.setIsHeaderLink(true);
        colDescription.setHeaderLinkTitle("Sort by: Type");
        colDescription.setDBColumnName("GENERAL_TYPE_ID");
        addColumn(colDescription);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        colSelect.setColumnWidth("5%");
        addColumn(colSelect);
    
    }//end of  setTableProperties()
 
}//end of TariffPlanPackageTable
