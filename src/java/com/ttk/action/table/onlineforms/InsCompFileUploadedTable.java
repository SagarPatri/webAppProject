 /**
  * @ (#) InsCompFileUploadedTable.java January 28, 2014
  * Project      : Vidal Health TPA
  * File         : InsCompFileUploadedTable.java
  * Author       : Thirumalai K P
  * Company      : Span Systems Corporation
  * Date Created : January 28, 2014
  *
  * @author       :Thirumalai K P
  * Modified by   :
  * Modified date :
  * Reason        : Table for viewing the File upload files in Healthcare company login -- ins file upload
  */

package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class InsCompFileUploadedTable  extends Table{

	private static final long serialVersionUID = 1L;

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for File Name
        Column colFileName = new Column("Healthcare Company Uploaded File Name");
        colFileName.setMethodName("getFileName");
        colFileName.setColumnWidth("20%");
        colFileName.setIsHeaderLink(true);
        colFileName.setHeaderLinkTitle("Sort by: File Name");
        colFileName.setIsLink(true);
        colFileName.setLinkTitle("View File");
        colFileName.setDBColumnName("INS_FILENAME");
        addColumn(colFileName);
        
        //Setting properties for Added Date
        Column colAddedDate =new Column("Added Date");
        colAddedDate.setMethodName("getAddedDate");
        colAddedDate.setColumnWidth("15%");
        colAddedDate.setIsHeaderLink(true);
        colAddedDate.setHeaderLinkTitle("Sort by: Added Date");
        colAddedDate.setDBColumnName("ADDED_DATE");
        addColumn(colAddedDate);
    
/*        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);*/
    }//end of  public void setTableProperties()

}//end of InsCompFileUploadedTable