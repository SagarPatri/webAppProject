/**
* @ (#) InsuranceCompanyTable.java Nov 21, 2005
* Project 		: TTK HealthCare Services
* File 			: InsuranceCompanyTable.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created 	: Nov 21, 2005
*
* @author 		: Pradeep R
* Modified by 	: 
* Modified date : 
* Reason 		: 
*/

package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

 

public class SwInsurancePricingTable extends Table {
	/**
	 * This creates the columnproperties objects for each and 
	 * every column and adds the column object to the table
	 */
	public void setTableProperties() {
		setRowCount(10);
		setCurrentPage(1);
		setPageLinkCount(10);

		
		Column colRefNumber = new Column("Pricing Reference No");
		colRefNumber.setMethodName("getPricingRefno");
		colRefNumber.setColumnWidth("30%");
		colRefNumber.setIsLink(true);
		colRefNumber.setLinkTitle("Edit Client");
		colRefNumber.setIsHeaderLink(true);
		colRefNumber.setHeaderLinkTitle("Sort by:  Pricing Reference No");
		colRefNumber.setDBColumnName("REF_NO");
		addColumn(colRefNumber);
		
		
		//Setting properties for Status
		Column colpreviousPolicy = new Column("Policy No");
		colpreviousPolicy.setMethodName("getPreviousPolicyNo");
		colpreviousPolicy.setColumnWidth("35%");
		colpreviousPolicy.setIsHeaderLink(true);
		colpreviousPolicy.setHeaderLinkTitle("Sort by : Policy No");
		colpreviousPolicy.setDBColumnName("PREV_POLICY_NO");
		addColumn(colpreviousPolicy);
				
		//Setting properties for Company Name 
		Column colCompanyName = new Column("Client Name");
		colCompanyName.setMethodName("getClientCode");
		colCompanyName.setColumnWidth("30%");
		colCompanyName.setIsLink(true);
		colCompanyName.setLinkTitle("Edit Client");
		colCompanyName.setIsHeaderLink(true);
		colCompanyName.setHeaderLinkTitle("Sort by Client ");
		colCompanyName.setDBColumnName("client_code");
		addColumn(colCompanyName);
		
		//Setting properties for check box
				Column colSelect = new Column("Select");
				colSelect.setComponentType("checkbox");
				colSelect.setComponentName("chkopt");		
				addColumn(colSelect); 		
		
	}//end of public void setTableProperties()
}//end of class InsuranceCompanyTable



