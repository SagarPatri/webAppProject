

package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class HospLinkDetailsTable extends Table
{
	private static final long serialVersionUID = 1L;
	/**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
    	setRowCount(25);
        setCurrentPage(1);
        setPageLinkCount(25);
        //Setting properties for Link Description
        Column colLinkDescription=new Column("Link Description");
        colLinkDescription.setMethodName("getConfigLinkDesc");
        colLinkDescription.setColumnWidth("50%");
       // colLinkDescription.setIsLink(true);
        colLinkDescription.setIsHeaderLink(true);
        colLinkDescription.setHeaderLinkTitle("Sort by: Link Description");
        colLinkDescription.setDBColumnName("LINK_DESCRIPTION");
        addColumn(colLinkDescription);
        
        //Setting properties for Type
        /*Column colType = new Column("Type");
        colType.setMethodName("getLinkTypeDesc");
        colType.setColumnWidth("30%");
        colType.setIsHeaderLink(true);
        colType.setHeaderLinkTitle("Sort by: Type");
        colType.setDBColumnName("TYPE");
        addColumn(colType);*/
        
        //Setting properties for Sort Order
        Column colOrderNumber = new Column("Priority");
        colOrderNumber.setMethodName("getOrderNumber");
        colOrderNumber.setColumnWidth("50%");
        colOrderNumber.setIsHeaderLink(true);
        colOrderNumber.setHeaderLinkTitle("Sort by: Sort Order");
        colOrderNumber.setDBColumnName("LINK_ORDER_NUMBER");
        addColumn(colOrderNumber);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }//end of setTableProperties()    
}//end of HospLinkDetailsTable()class

