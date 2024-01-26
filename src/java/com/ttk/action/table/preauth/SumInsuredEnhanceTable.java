/**
 * @ (#) SumInsuredEnhanceTable.javaMay 12, 2006
 * Project      : TTK HealthCare Services
 * File         : SumInsuredEnhanceTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : May 12, 2006
 *
 * @author       :  Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class SumInsuredEnhanceTable extends Table
{
    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(50);
        setCurrentPage(1);
        setPageLinkCount(10);

        // Setting the properties for Scheme No
        Column colPolicyNo=new Column("Scheme No.");
        colPolicyNo.setMethodName("getPolicyNo");
        colPolicyNo.setColumnWidth("15%");
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);

        //Setting the properties for start date
        Column colStartDate=new Column("Start Date");
        colStartDate.setMethodName("getStartDate");
        colStartDate.setColumnWidth("18%");
        colStartDate.setDBColumnName("EFFECTIVE_FROM_DATE");
        addColumn(colStartDate);

        //Setting the properties for End date
        Column colEndDate=new Column("End Date");
        colEndDate.setMethodName("getEndDate");
        colEndDate.setColumnWidth("18%");
        colEndDate.setDBColumnName("EFFECTIVE_TO_DATE");
        addColumn(colEndDate);

        //Setting the properties for Sum Insured
        Column colSumInsured =new Column("Sum Insured(Rs)");
        colSumInsured.setMethodName("getSumInsured");
        colSumInsured.setColumnWidth("14%");
        colSumInsured.setDBColumnName("FLOATER_SUM_INSURED");
        addColumn(colSumInsured);
    }// end of setTableProperties()
}//end of SumInsuredEnhanceTable class
