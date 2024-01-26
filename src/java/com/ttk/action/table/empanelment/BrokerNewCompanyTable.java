
/**
* @ (#) BrokerNewCompanyTable.java Nov 21, 2005
* Project 		: TTK HealthCare Services
* File 			: BrokerNewCompanyTable.java
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

 

public class BrokerNewCompanyTable extends Table {
	/**
	 * This creates the columnproperties objects for each and 
	 * every column and adds the column object to the table
	 */
	public void setTableProperties() {
		setRowCount(20);
		setCurrentPage(1);
		setPageLinkCount(20);
		
		//Setting properties for Company Name 
		Column colCompanyName = new Column("Broker Name");
		colCompanyName.setMethodName("getCompanyName");
		colCompanyName.setColumnWidth("30%");
		colCompanyName.setIsLink(true);
		colCompanyName.setLinkTitle("Edit Company Name");
		colCompanyName.setIsHeaderLink(true);
		colCompanyName.setHeaderLinkTitle("Sort by Broker Name");
		colCompanyName.setDBColumnName("INS_COMP_NAME");
		addColumn(colCompanyName);
		
		//Setting properties for Company Code 
		Column colCompanyCode = new Column("Broker Code");
		colCompanyCode.setMethodName("getCompanyCodeNbr");
		colCompanyCode.setColumnWidth("20%");
		colCompanyCode.setIsHeaderLink(true);
		colCompanyCode.setHeaderLinkTitle("Sort by Broker Code");
		colCompanyCode.setDBColumnName("INS_COMP_CODE_NUMBER");
		addColumn(colCompanyCode);
		
		//Setting properties for Broker Authority 
		Column colBrokerAuthority = new Column("Broker Authority");
		colBrokerAuthority.setMethodName("getRegAuthority");
		colBrokerAuthority.setColumnWidth("20%");
		colBrokerAuthority.setIsHeaderLink(true);
		colBrokerAuthority.setHeaderLinkTitle("Sort by Broker Authority");
		colBrokerAuthority.setDBColumnName("BROKER_AUTHORITY");
		addColumn(colBrokerAuthority);

		//Setting properties for Insurance Company
		Column colEmpanelmentDate = new Column("Empanelment Date");
		colEmpanelmentDate.setMethodName("getFormattedEmpanelmentDate");
		colEmpanelmentDate.setColumnWidth("20%");
		colEmpanelmentDate.setIsHeaderLink(true);
		colEmpanelmentDate.setHeaderLinkTitle("Sort by: Empanelment Date");
		colEmpanelmentDate.setDBColumnName("EMPANELED_DATE");
		addColumn(colEmpanelmentDate);
		
		//Setting properties for Status
		Column colStatus = new Column("Status");
		colStatus.setMethodName("getActiveInactive");
		colStatus.setColumnWidth("20%");
		colStatus.setIsHeaderLink(true);
		colStatus.setHeaderLinkTitle("Sort by Status");
		colStatus.setDBColumnName("ACTIVE_YN");
		addColumn(colStatus);
		
		//Setting properties for check box
		Column colSelect = new Column("Select");
		colSelect.setComponentType("checkbox");
		colSelect.setComponentName("chkopt");		
		addColumn(colSelect); 				
	}//end of public void setTableProperties()
}//end of class BrokerNewCompanyTable



