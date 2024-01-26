/**
 * @ (#) 1352 Feb 15, 2006
 * Project       : TTK HealthCare Services
 * File          : EmployeeUploadFileList.java
 * Reason        :File Upload console in Employee Login
 */

package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;
//1352
public class FileUploadedTable  extends Table{

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for FileName
        Column colFileName = new Column("FileName.");
        colFileName.setMethodName("getFileName");
        colFileName.setColumnWidth("40%");
        colFileName.setIsHeaderLink(true);
        colFileName.setHeaderLinkTitle("Sort by: File Name.");
        colFileName.setIsLink(true);
        colFileName.setLinkTitle("View File");
        colFileName.setDBColumnName("VI_FILENAME");
        addColumn(colFileName);
        
        //Setting properties for Remarks
        Column colRemarks =new Column("Remarks");
        colRemarks.setMethodName("getRemarks");
        colRemarks.setColumnWidth("30%");
        colRemarks.setIsHeaderLink(true);
       // colReferenceNbr.setHeaderLinkTitle("Sort by: Reference No.");
        colRemarks.setDBColumnName("REMARKS");
        addColumn(colRemarks);
        
        
        //Setting properties for Added Date
        Column colAddedDate =new Column("Added Date");
        colAddedDate.setMethodName("getAddedDate");
        colAddedDate.setColumnWidth("30%");
        colAddedDate.setIsHeaderLink(true);
       // colAddedDate.setHeaderLinkTitle("Sort by: Reference No.");
        colAddedDate.setDBColumnName("ADDED_DATE");
        addColumn(colAddedDate);
    
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);

    
    }//end of  public void setTableProperties()

}//end of FileUploadedTable
