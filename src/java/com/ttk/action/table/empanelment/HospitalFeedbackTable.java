/**
 * @ (#) HospitalFeedbackTable.javaSep 20, 2005
 * Project      : TTK HealthCare Services
 * File         : HospitalFeedbackTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Sep 20, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : Arun K N
 * Modified date : Oct 08, 2005
 * Reason        : for fixing some problems
 */

package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * 
 * This class provides the information of Hospital feedback table
 * 
 */
public class HospitalFeedbackTable extends Table
{
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(20);
        //Setting properties for feedback date
        Column colFeedbackDate = new Column("Feedback Date");
        colFeedbackDate.setMethodName("getFeedbackDate");
        colFeedbackDate.setColumnWidth("60%");
        colFeedbackDate.setIsLink(true);
        //colFeedbackDate.setLinkTitle("Edit User");
        colFeedbackDate.setIsHeaderLink(true);
        colFeedbackDate.setHeaderLinkTitle("Sort by Feedback Date");
        colFeedbackDate.setDBColumnName("FEEDBACK_DATE");
        addColumn(colFeedbackDate);
        
        //Setting properties for ttk rating
        Column colTpaServiceRating = new Column("Vidal Health Rating");
        colTpaServiceRating.setMethodName("getRating");
        colTpaServiceRating.setColumnWidth("40%");
        colTpaServiceRating.setDBColumnName("TTK_RATING");
        addColumn(colTpaServiceRating);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
        
    }//end of public void setTableProperties()
}// end of class HospitalFeedbackTable 
