/**
 * @ (#) InsFeedbackTable.javaNov 12, 2005
 * Project      : TTK HealthCare Services
 * File         : InsFeedbackTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Nov 12, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * 
 * 
 * 
 */
public class InsFeedbackTable extends Table
{

    
    public void setTableProperties()
    {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(20);
        
        //Setting properties for circular date
        Column colDate = new Column("Feedback Date ");
        colDate.setMethodName("getFeebackRecievedDate");
        colDate.setColumnWidth("100%");
        colDate.setIsLink(true);
        colDate.setIsHeaderLink(true);
        colDate.setHeaderLinkTitle("Sort by Feedback Date");
        colDate.setDBColumnName("FEEDBACK_RECEIVED_DATE");
        addColumn(colDate);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
        
    }

}
