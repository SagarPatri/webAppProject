/**
 * @ (#) ActivityCodeTable.java June 18, 2015
 * Project      : Project-X
 * File         : ActivityCodeTable.java
 * Author       : Nagababu K
 * Company      : Vidal Health TPA Pvt. Ltd.
 * Date Created : June 18, 2015
 *
 * @author       :  Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class ActivityCodeListTable extends Table
{
    /**
	 * 
	 */
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

        //Setting properties for Code
        
        Column colActivityTypeCode=new Column("Activity Type Code");
        colActivityTypeCode.setMethodName("getActivityTypeCode");
        colActivityTypeCode.setColumnWidth("10%");
     //   colActivityTypeCode.setIsLink(true);
     //   colActivityTypeCode.setIsHeaderLink(true);      
     //   colActivityTypeCode.setHeaderLinkTitle("Sort by:Activity Code");
        colActivityTypeCode.setDBColumnName("activity_type_code");
        addColumn(colActivityTypeCode);
        
        
        Column colActivityCode=new Column("Activity Code");
        colActivityCode.setMethodName("getActivityCode");
        colActivityCode.setColumnWidth("10%");
        colActivityCode.setIsLink(true);
        colActivityCode.setIsHeaderLink(true);      
        colActivityCode.setHeaderLinkTitle("Sort by:Activity Code");
        colActivityCode.setDBColumnName("ACTIVITY_CODE");
        addColumn(colActivityCode);
        
        Column colDescription=new Column("Description");
        colDescription.setMethodName("getActivityCodeDesc");
        colDescription.setColumnWidth("80%");
        colDescription.setDBColumnName("ACTIVITY_DESCRIPTION");
        addColumn(colDescription);
/*
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);*/
    }// end of public void setTableProperties()

}// end of ActivityCodeTable class
