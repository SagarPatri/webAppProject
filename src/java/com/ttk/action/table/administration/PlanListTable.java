/**
 * @ (#) PlanListTable.java   June 30, 2008
 * Project        : TTK HealthCare Services
 * File           : PlanListTable.java
 * Author         : Sanjay.G.Nayaka
 * Company        : Span Systems Corporation
 * Date Created   : June 30, 2008
 *
 * @author        : Sanjay.G.Nayaka
 * Modified by    : 
 * Modified date  :
 * Reason         : 
 */

package com.ttk.action.table.administration;
import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Plan list
 * 
 */
public class PlanListTable extends Table
{
   /**
    *  This method creates the column properties objects for each and 
    *  every column and adds the column object to the table
    */	
   public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //Setting properties for Plan Name
        Column colPlanName = new Column("Plan Name");
        colPlanName.setMethodName("getProdPlanName");
        colPlanName.setColumnWidth("20%");
        colPlanName.setIsHeaderLink(true);
        colPlanName.setHeaderLinkTitle("Sort by: Plan Name");
        colPlanName.setIsLink(true);
        colPlanName.setLinkTitle("Edit Plan");
        colPlanName.setDBColumnName("PROD_PLAN_NAME");
        addColumn(colPlanName);
        
        //Setting properties for Plan Code
        Column colPlanCode = new Column("Plan Code");
        colPlanCode.setMethodName("getPlanCode");
        colPlanCode.setColumnWidth("10%");
        colPlanCode.setIsHeaderLink(true);
        colPlanCode.setHeaderLinkTitle("Sort by: Plan Code");
        colPlanCode.setDBColumnName("PLAN_CODE");
        addColumn(colPlanCode);
        
        //Setting properties for From Age
        Column colFromAge =new Column("From Age");
        colFromAge.setMethodName("getFormattedFromAge");
        colFromAge.setColumnWidth("10%");
        colFromAge.setIsHeaderLink(true);
        colFromAge.setHeaderLinkTitle("Sort by: From Age");
        colFromAge.setDBColumnName("FROM_AGE");    
        addColumn(colFromAge);
        
        //Setting properties for To Age
        Column colToAge =new Column("To Age");
        colToAge.setMethodName("getFormattedToAge");
        colToAge.setColumnWidth("10%");
        colToAge.setIsHeaderLink(true);
        colToAge.setHeaderLinkTitle("Sort by: To Age");
        colToAge.setDBColumnName("TO_AGE");    
        addColumn(colToAge);
        
        //Setting properties for Plan Amount
        Column colPlanAmount =new Column("Plan Amt. (Rs)");
        colPlanAmount.setMethodName("getPlanAmount");
        colPlanAmount.setColumnWidth("15%");
        colPlanAmount.setIsHeaderLink(true);
        colPlanAmount.setHeaderLinkTitle("Sort by: Plan Amount");
        colPlanAmount.setDBColumnName("PLAN_AMOUNT");    
        addColumn(colPlanAmount);
    }// end of setTableProperties()
}// end of PlanDetailsTable
