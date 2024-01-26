
/**
 * @ (#) UploadMOUDocsAction.java 31 Dec 2014
 * Project      : TTK HealthCare Services
 * File         :UploadMOUDocsAction.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 31 Dec 2014
 *
 * @author       : Kishor kumar S H
 * Modified by   : 
 * Modified date : 31 Dec 2014
 */
package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class MouUploadFilesTable extends Table{
	/**
     * This creates the column properties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
    	setRowCount(25);
        setCurrentPage(1);
        setPageLinkCount(25);
        //Setting properties for Link Description
        Column colDescription=new Column("Description");
        colDescription.setMethodName("getDescription");
        colDescription.setColumnWidth("25%");
  //      colDescription.setIsLink(true);
        //colDescription.setIsHeaderLink(true);
        colDescription.setHeaderLinkTitle("Sort by: Description");
        colDescription.setDBColumnName("DESCRIPTION");
        addColumn(colDescription);
        //Setting properties for File Name
        Column colFileName = new Column("File Name");
        colFileName.setMethodName("getFileName");
        colFileName.setColumnWidth("25%");
        colFileName.setIsLink(true);
        //colFileName.setIsHeaderLink(true);
        colFileName.setHeaderLinkTitle("Sort by: File Name");
        colFileName.setDBColumnName("File_Name");
        addColumn(colFileName);
        
      //Setting properties for File Name
        Column colDateTime = new Column("Date and Time");
        colDateTime.setMethodName("getDateTime");
        colDateTime.setColumnWidth("25%");
        //colDateTime.setIsHeaderLink(true);
        colDateTime.setHeaderLinkTitle("Sort by: Date and Time");
        colDateTime.setDBColumnName("added_date");
        addColumn(colDateTime);
      //Setting properties for File Name
        Column colUserId = new Column("User Id");
        colUserId.setMethodName("getUserId");
        colUserId.setColumnWidth("25%");
        //colUserId.setIsHeaderLink(true);
        colUserId.setHeaderLinkTitle("Sort by: User Id");
        colUserId.setDBColumnName("added_by");
        addColumn(colUserId);
       // Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);       
    }//end of setTableProperties()
}//end of MouUploadFilesTable
