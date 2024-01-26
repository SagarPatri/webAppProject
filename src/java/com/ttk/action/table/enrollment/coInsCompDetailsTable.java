/**
 * @ (#) PolicyEnrollmentTable.java Feb 1, 2006
 * Project      : TTK HealthCare Services
 * File         : PolicyEnrollmentTable
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Feb 1, 2006
 *
 * @author       :  Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class consists of the information requied to prepare grid table
 * for Policy Search screens of Individual Policy,Ind. Policy as Group,
 * Corporate Policy and Non-Corporate Policy of enrollment module
 * 
 */
public class coInsCompDetailsTable extends Table
{
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //The order of the column should remain as same as here, if any new colum needs to be added then add at the end
        
        //Setting properties for Scheme No 
        Column colCompName=new Column("Company Name");
        colCompName.setMethodName("getCompName");
        colCompName.setColumnWidth("34%");
        colCompName.setIsHeaderLink(true);
        colCompName.setHeaderLinkTitle("Sort by: Company Name");
        colCompName.setDBColumnName("POLICY_NUMBER");
        addColumn(colCompName);
        
        //Setting properties for Policy Category 
        Column colCompId=new Column("Company Id");
        colCompId.setMethodName("getCompID");
        colCompId.setColumnWidth("34%");
        colCompId.setIsHeaderLink(true);
        colCompId.setHeaderLinkTitle("Sort by: Company Id");
        colCompId.setDBColumnName("CLASSIFICATION");
        addColumn(colCompId);
        
      //Start Ak
		//Setting properties for Product_Name
		Column colPercentage = new Column("Percentage");
		colPercentage.setMethodName("getCoInsPercentage");
		colPercentage.setColumnWidth("30%");
		colPercentage.setIsHeaderLink(true);
		colPercentage.setHeaderLinkTitle("Sort by Percentage");
		colPercentage.setDBColumnName("Product_name");
		addColumn(colPercentage);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        colSelect.setColumnWidth("2%");
        addColumn(colSelect);
        
    }//end of setTableProperties()
}//end of coInsCompDetailsTable.java
