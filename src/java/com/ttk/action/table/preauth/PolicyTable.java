/**
 * @ (#) PolicyTable.java 9th May 2006
 * Project      : TTK HealthCare Services
 * File         : PolicyTable.java
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : 9th May 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Policy details
 *
 */
public class PolicyTable extends Table{

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Scheme No
        Column colPolicyNo = new Column("Scheme No.");
        colPolicyNo.setMethodName("getPolicyNo");
        colPolicyNo.setColumnWidth("60%");
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setHeaderLinkTitle("Sort by: Scheme No.");
        colPolicyNo.setIsLink(true);
        colPolicyNo.setLinkTitle("View History");
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);

        //Setting properties for Start Date
        Column colStartDate = new Column("Start Date");
        colStartDate.setMethodName("getStartDate");
        colStartDate.setColumnWidth("20%");
        colStartDate.setIsHeaderLink(true);
        colStartDate.setHeaderLinkTitle("Sort by: Start Date");
        colStartDate.setDBColumnName("EFFECTIVE_FROM_DATE");
        addColumn(colStartDate);

        //Setting properties for End Date
        Column colEndDate =new Column("End Date");
        colEndDate.setMethodName("getEndDate");
        colEndDate.setColumnWidth("20%");
        colEndDate.setIsHeaderLink(true);
        colEndDate.setHeaderLinkTitle("Sort by: End Date");
        colEndDate.setDBColumnName("EFFECTIVE_TO_DATE");
        addColumn(colEndDate);

    }//end of setTableProperties()

}//end of PolicyTable
