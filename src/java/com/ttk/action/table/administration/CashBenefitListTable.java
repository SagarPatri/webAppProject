//KOC 1270 for hospital cash benefit
/**
 * @ (#) CashBenefitListTable.java   June 30, 2008
 * Project        : TTK HealthCare Services
 * File           : CashBenefitListTable.java
 * Author         : 
 * Company        : 
 * Date Created   : 
 *
 * @author        : 
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
public class CashBenefitListTable extends Table
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
        colPlanName.setDBColumnName("CASH_BENEFIT_PLAN_NAME");
        addColumn(colPlanName);
        
        //Setting properties for Plan Code
        Column colPlanCode = new Column("Plan Code");
        colPlanCode.setMethodName("getPlanCode");
        colPlanCode.setColumnWidth("10%");
        colPlanCode.setIsHeaderLink(true);
        colPlanCode.setHeaderLinkTitle("Sort by: Plan Code");
        colPlanCode.setDBColumnName("cash_benefit_plan_code");
        addColumn(colPlanCode);
        
        //Setting properties for From Age
        Column colFromAge =new Column("From Age");
        colFromAge.setMethodName("getFormattedFromAge");
        colFromAge.setColumnWidth("10%");
        colFromAge.setIsHeaderLink(true);
        colFromAge.setHeaderLinkTitle("Sort by: From Age");
        colFromAge.setDBColumnName("from_age");    
        addColumn(colFromAge);
        
        //Setting properties for To Age
        Column colToAge =new Column("To Age");
        colToAge.setMethodName("getFormattedToAge");
        colToAge.setColumnWidth("10%");
        colToAge.setIsHeaderLink(true);
        colToAge.setHeaderLinkTitle("Sort by: To Age");
        colToAge.setDBColumnName("to_age");    
        addColumn(colToAge);

    }// end of setTableProperties()
}// end of CashBenefitListTable
