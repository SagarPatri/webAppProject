/**
 * @ (#) HospitalSearchAction.java Sep 20, 2005
 * Project       : TTK HealthCare Services
 * File          : HospitalSearchAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 20, 2005
 *
 * @author       : Srikanth H M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of sbb_vendor table
 */
public class PartnerSearchTable extends Table // implements TableObjectInterface,Serializable
{
	/**
	 * This creates the columnproperties objects for each and 
	 * every column and adds the column object to the table
	 */
	public void setTableProperties()
	{
		setRowCount(20);
		setCurrentPage(1);
		setPageLinkCount(20);
		// Setting properties for Partner name		
		Column colPartnerName = new Column("Partner Name");
		colPartnerName.setMethodName("getPartnerName");
		colPartnerName.setColumnWidth("30%");
		colPartnerName.setIsLink(true);
		colPartnerName.setLinkTitle("Edit Partner");
		colPartnerName.setIsHeaderLink(true);
		colPartnerName.setHeaderLinkTitle("Sort by Partner Name");
		colPartnerName.setDBColumnName("PARTNER_NAME");
		addColumn(colPartnerName);
		
		//Setting properties for empanelment number
		Column colEmpanelmentNo = new Column("Empanelment No.");
		colEmpanelmentNo.setMethodName("getEmplNumber");
        colEmpanelmentNo.setColumnWidth("15%");
		colEmpanelmentNo.setIsHeaderLink(true);
		colEmpanelmentNo.setHeaderLinkTitle("Sort by Empanelment No");
		colEmpanelmentNo.setDBColumnName("EMPANEL_NUMBER");
		addColumn(colEmpanelmentNo);
		

		//Setting properties for Insurance Company
		Column colEmpanelmentDate = new Column("Empanelment Date");
		colEmpanelmentDate.setMethodName("getFormattedEmpanelmentDate");
		colEmpanelmentDate.setColumnWidth("20%");
		colEmpanelmentDate.setIsHeaderLink(true);
		colEmpanelmentDate.setHeaderLinkTitle("Sort by: Empanelment Date");
		colEmpanelmentDate.setDBColumnName("EMPANELED_DATE");
		addColumn(colEmpanelmentDate);
		
		//Setting properties for Partner Status
		Column colStatus = new Column("Status");
		colStatus.setMethodName("getPartnerStatus");
		colStatus.setColumnWidth("20%");
		colStatus.setIsHeaderLink(true);
		colStatus.setHeaderLinkTitle("Sort by Status");
		colStatus.setDBColumnName("EMPANEL_DESCRIPTION");
		addColumn(colStatus);
	
		//Setting properties for check box
		Column colSelect = new Column("Select");
		colSelect.setComponentType("checkbox");
		colSelect.setComponentName("chkopt");		
		addColumn(colSelect); 				
	}//end of public void setTableProperties()
}//end of class PartnerSearchTable
