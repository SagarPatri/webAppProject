package com.ttk.action.table.maintenance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ConfigurationTable extends Table
{
	public void setTableProperties()
    {
	    setRowCount(25);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //Setting properties for TTK Branch
        Column colTTKBranch = new Column("Vidal Health Branch");
        colTTKBranch.setMethodName("getOfficeName");
        colTTKBranch.setColumnWidth("20%");
        colTTKBranch.setIsLink(true);
        colTTKBranch.setLinkTitle("Edit Vidal Health Branch");
        colTTKBranch.setIsHeaderLink(true);
        colTTKBranch.setHeaderLinkTitle("Sort by: Vidal Health Branch");
        colTTKBranch.setDBColumnName("OFFICE_NAME");
        addColumn(colTTKBranch);
        
        //Setting properties for Config Param1
        Column colConfigParam1 = new Column("Config Param1");
        colConfigParam1.setMethodName("getStrConfigParam1");
        colConfigParam1.setColumnWidth("20%");
        colConfigParam1.setIsHeaderLink(true);
        colConfigParam1.setHeaderLinkTitle("Sort by: Config Param1");
        colConfigParam1.setDBColumnName("CONFIG_PARAM_1");
        addColumn(colConfigParam1);
        
        //Setting properties for Config Param2
        Column colConfigParam2 = new Column("Config Param2");
        colConfigParam2.setMethodName("getStrConfigParam2");
        colConfigParam2.setColumnWidth("20%");
        colConfigParam2.setIsHeaderLink(true);
        colConfigParam2.setHeaderLinkTitle("Sort by: Config Param2");
        colConfigParam2.setDBColumnName("CONFIG_PARAM_2");
        addColumn(colConfigParam2);
        
        //Setting properties for Config Param3
        Column colConfigParam3 = new Column("Config Param3");
        colConfigParam3.setMethodName("getStrConfigParam3");
        colConfigParam3.setColumnWidth("20%");
        colConfigParam3.setIsHeaderLink(true);
        colConfigParam3.setHeaderLinkTitle("Sort by: Config Param3");
        colConfigParam3.setDBColumnName("CONFIG_PARAM_3");
        addColumn(colConfigParam3);
		
		//Setting properties for Config Param4  koc11 koc 11
        Column colConfigParam4 = new Column("Config Param4");
        colConfigParam4.setMethodName("getStrConfigParam4");
        colConfigParam4.setColumnWidth("20%");
        colConfigParam4.setIsHeaderLink(true);
        colConfigParam4.setHeaderLinkTitle("Sort by: Config Param4");
        colConfigParam4.setDBColumnName("CONFIG_PARAM_4");
        addColumn(colConfigParam4);
    }//end of setTableProperties()
}//end of ConfigurationTable class
