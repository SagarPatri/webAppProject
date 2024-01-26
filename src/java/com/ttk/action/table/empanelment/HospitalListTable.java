//Insurance_corporate_wise_hosp_network
/**
 * @ (#) HospitalTable.java Nov 08, 2005
 * Project       : TTK HealthCare Services
 * File          : HospitalTable.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Nov 08, 2005
 *
 * @author       : Bhaskar Sandra
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.action.table.empanelment;
import com.ttk.action.table.Table;
import com.ttk.action.table.Column;


public class HospitalListTable extends Table {
	/**
	 * This creates the columnproperties objects for each and 
	 * every column and adds the column object to the table
	 */

	public void setTableProperties()
	{
	setRowCount(20);
	setCurrentPage(1);
	setPageLinkCount(20);
	Column colHospName =new Column("Provider Name");           
	colHospName.setMethodName("getHospName");                  
	colHospName.setColumnWidth("40%");                              
	colHospName.setIsHeaderLink(true);                    
	colHospName.setHeaderLinkTitle("Sort by: Provider Name");
	colHospName.setDBColumnName("hosp_name");                 
	addColumn(colHospName);
	
	Column colCity=new Column("Area");                    
	colCity.setMethodName("getCityDesc");
	colCity.setColumnWidth("15%");
	colCity.setIsHeaderLink(true);
	colCity.setHeaderLinkTitle("Sort by: Area");
	colCity.setDBColumnName("CITY_DESCRIPTION");
	addColumn(colCity);
	
	Column colEmpNo =new Column("Empanelment No.");          
	colEmpNo.setMethodName("getEmpanelmentNO");                     
	colEmpNo.setColumnWidth("20%");   
	colEmpNo.setIsHeaderLink(true);
	colEmpNo.setHeaderLinkTitle("Sort by: Empanelment No.");  
	colEmpNo.setDBColumnName("EMPANEL_NUMBER");                
	addColumn(colEmpNo);
	
	Column colTtkBranch =new Column("Emirate/State");          
	colTtkBranch.setMethodName("getOfficeInfo");                 
	colTtkBranch.setColumnWidth("20%");                    
	colTtkBranch.setIsHeaderLink(true);                    
	colTtkBranch.setHeaderLinkTitle("Sort by: Emirate/State");
	colTtkBranch.setDBColumnName("state_type_id");            
	addColumn(colTtkBranch);

    
//	Setting properties for check box
	Column colSelect = new Column("Select");
	colSelect.setComponentType("checkbox");
	colSelect.setComponentName("chkopt");		
	addColumn(colSelect); 				
}//end of public void setTableProperties()
}//end of class HospitalTable
