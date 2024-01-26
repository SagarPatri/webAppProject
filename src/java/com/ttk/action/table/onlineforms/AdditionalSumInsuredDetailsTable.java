/**
 * @ (#) AdditionalSumInsuredDetailsTable.java
 * Project       : TTK HealthCare Services
 * File          : AdditionalSumInsuredDetailsTable.java
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : Jan 18, 2008
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class AdditionalSumInsuredDetailsTable extends Table {
    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */

        public void setTableProperties()
        {
            setRowCount(1000);
            setCurrentPage(1);
            setPageLinkCount(10);

            //Setting properties for Plan Name
            Column colPlanName = new Column("Plan Name");
            colPlanName.setMethodName("getProdPlanName");
            colPlanName.setColumnWidth("20%"); 
            /*colPlanName.setIsHeaderLink(true);
            colPlanName.setHeaderLinkTitle("Sort by: Plan Name");*/
            colPlanName.setDBColumnName("PROD_PLAN_NAME");
            addColumn(colPlanName);

            //Setting properties for
            Column colAge=new Column("Age");
            colAge.setMethodName("getCombineAge");
            colAge.setColumnWidth("15%");
           /* colAge.setDBColumnName("FROM_AGE");
            colAge.setDBColumnName("TO_AGE");*/
            addColumn(colAge);

            //Setting properties for Sum Insured (Rs)
            Column colSumInsured = new Column("Top-up Sum Insured Opted");
            colSumInsured.setMethodName("getPlanAmount");
            colSumInsured.setColumnWidth("22%");
            colSumInsured.setDBColumnName("PLAN_AMOUNT");
            addColumn(colSumInsured);

            //Setting properties for Premium
            Column colPremium = new Column("Annual Premium (Rs)");
            colPremium.setMethodName("getPlanPremium");
            colPremium.setColumnWidth("19%");
            colPremium.setDBColumnName("PLAN_PREMIUM");
            addColumn(colPremium);
			
			//Setting properties for Pro-rata Premium
            Column colProrata = new Column("Pro rata Premium (Rs)");
            colProrata.setMethodName("getProRata");
            colProrata.setColumnWidth("19%");
            colProrata.setDBColumnName("PRORATA");
            addColumn(colProrata);

//          Setting properties for check box
    		Column colSelect = new Column("Select");
    		colSelect.setComponentType("radio");
    		colSelect.setComponentName("chkopt");
    		colSelect.setColumnWidth("5%");
    		addColumn(colSelect);

        }//end of public void setTableProperties()
    }//end of class OnlinePoliciesTable
