/**
 * @ (#) InsValidityTable.javaNov 16, 2005
 * Project      : TTK HealthCare Services
 * File         : InsValidityTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Nov 16, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class ValidityTable extends Table
{
    public void setTableProperties()
    {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(20);

        //Setting properties for circular date
        Column colFromDate = new Column("Valid From ");
        colFromDate.setMethodName("getStartDate");
        colFromDate.setColumnWidth("50%");
        colFromDate.setIsHeaderLink(true);
        colFromDate.setHeaderLinkTitle("Sort by Valid From");
        colFromDate.setDBColumnName("VALID_FROM");
        addColumn(colFromDate);

        //Setting properties for circular date
        Column colToDate = new Column("Valid To ");
        colToDate.setMethodName("getEndDate");
        colToDate.setColumnWidth("50%");
        colToDate.setIsHeaderLink(true);
        colToDate.setHeaderLinkTitle("Sort by Valid To");
        colToDate.setDBColumnName("VALID_TO");
        addColumn(colToDate);
    }// end of setTableProperties()
}// end of InsValidityTable class
