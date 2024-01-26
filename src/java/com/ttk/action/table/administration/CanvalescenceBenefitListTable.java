//KOC 1270 for hospital cash benefit
/**
 * @ (#) CanvalescenceBenefitListTable.java   MAY 27, 2013
 * Project        : TTK HealthCare Services
 * File           : CanvalescenceBenefitListTable.java
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
@SuppressWarnings("serial")
public class CanvalescenceBenefitListTable extends Table
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
        colPlanName.setMethodName("getProdPlanConvName");
        colPlanName.setColumnWidth("20%");
        colPlanName.setIsHeaderLink(true);
        colPlanName.setHeaderLinkTitle("Sort by: Plan Name");
        colPlanName.setIsLink(true);
        colPlanName.setLinkTitle("Edit Plan");
        colPlanName.setDBColumnName("conv_benf_plan_name");
        addColumn(colPlanName);
        
        //Setting properties for Plan Code
        Column colPlanCode = new Column("Plan Code");
        colPlanCode.setMethodName("getPlanConvCode");
        colPlanCode.setColumnWidth("10%");
        colPlanCode.setIsHeaderLink(true);
        colPlanCode.setHeaderLinkTitle("Sort by: Plan Code");
        colPlanCode.setDBColumnName("conv_benf_plan_code");
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
        Column colPlanAmount =new Column("Canvalescence Amt. (Rs)");
        colPlanAmount.setMethodName("getConveyance");
        colPlanAmount.setColumnWidth("15%");
        colPlanAmount.setIsHeaderLink(true);
        colPlanAmount.setHeaderLinkTitle("Sort by: Plan Amount");
        colPlanAmount.setDBColumnName("conv_amt");    
        addColumn(colPlanAmount);
    }// end of setTableProperties()
}// end of CanvalescenceBenefitListTable
