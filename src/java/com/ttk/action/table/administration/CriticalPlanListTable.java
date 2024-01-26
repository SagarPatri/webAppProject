/**
 * @ (#) CriticalPlanListTable   June 30, 2008
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
public class CriticalPlanListTable extends Table
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
        
        //Setting properties for From Age
        Column colFromAge =new Column("From Age");
        colFromAge.setMethodName("getFormattedFromAge");
        colFromAge.setColumnWidth("10%");
        colFromAge.setIsHeaderLink(true);
        colFromAge.setHeaderLinkTitle("Sort by: From Age");
        colFromAge.setIsLink(true);
        colFromAge.setLinkTitle("Edit CriticalPlan");
        colFromAge.setDBColumnName("FRM_AGE");    
        addColumn(colFromAge);
        
        //Setting properties for To Age
        Column colToAge =new Column("To Age");
        colToAge.setMethodName("getFormattedToAge");
        colToAge.setColumnWidth("10%");
        colToAge.setIsHeaderLink(true);
        colToAge.setHeaderLinkTitle("Sort by: To Age");
        colToAge.setDBColumnName("TO_AGE");    
        addColumn(colToAge);
        
      
        //Setting properties for Plan Name
        /*Column colGroupName = new Column("Group Name");
        colGroupName.setMethodName("getCriticalTypeID");
        colGroupName.setColumnWidth("20%");
        colGroupName.setIsHeaderLink(true);
        colGroupName.setHeaderLinkTitle("Sort by: CriticalTypeID");
        colGroupName.setIsLink(true);
        colGroupName.setLinkTitle("Edit CriticalPlan");
        colGroupName.setDBColumnName("CRITICAL_GRP");
        addColumn(colGroupName);*/
        
         
        //Setting properties for Plan Amount
        /*Column colBenefitAmount =new Column("Critical Benefit Amt. (Rs)");
        colBenefitAmount.setMethodName("getCriticalBenefitAmount");
        colBenefitAmount.setColumnWidth("15%");
        colBenefitAmount.setIsHeaderLink(true);
        colBenefitAmount.setHeaderLinkTitle("Sort by: BenefitAmount");
        colBenefitAmount.setDBColumnName("AMOUNT");    
        addColumn(colBenefitAmount);*/
    }// end of setTableProperties()
}// end of CriticalPlanListTable
