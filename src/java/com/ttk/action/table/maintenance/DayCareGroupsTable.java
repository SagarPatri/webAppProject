package com.ttk.action.table.maintenance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class DayCareGroupsTable extends Table{

	 public void setTableProperties()
	    {
		    setRowCount(10);
	        setCurrentPage(1);
	        setPageLinkCount(10);
	        
	        //Setting properties for Name
	        Column colName = new Column("Name");
	        colName.setMethodName("getGroupName");
	        colName.setColumnWidth("42%");
	        colName.setIsLink(true);
	        colName.setLinkTitle("Edit Group Name");
	        colName.setIsHeaderLink(true);
	        colName.setHeaderLinkTitle("Sort by: Name");
	        colName.setDBColumnName("GROUP_NAME");
	        addColumn(colName);
	        
	        //Setting properties for Description
	        Column colDescription = new Column("Description");
	        colDescription.setMethodName("getGroupDesc");
	        colDescription.setColumnWidth("58%");
	        colDescription.setIsHeaderLink(true);
	        colDescription.setHeaderLinkTitle("Sort by: Description");
	        colDescription.setDBColumnName("GROUP_DESCRIPTION");
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
	            
	    }//end of public void setTableProperties()

}//end of class DayCareGroupsTable
