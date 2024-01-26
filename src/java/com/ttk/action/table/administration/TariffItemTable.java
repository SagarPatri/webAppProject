/**
 * @ (#) TariffItemTable.java Sep 29, 2005
 * Project       : TTK HealthCare Services
 * File          : TariffItemTable.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 29, 2005
 *
 * @author       : Srikanth H M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of sbb_vendor table
 */

public class TariffItemTable extends Table // implements TableObjectInterface,Serializable
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
		
		//Setting properties tariff name
		Column colName = new Column("Name");
		colName.setMethodName("getTariffItemName");
		colName.setColumnWidth("53%");
		colName.setIsLink(true);
		colName.setLinkTitle("Edit Name");
		colName.setIsHeaderLink(true);
		colName.setHeaderLinkTitle("Sort by Name");
		colName.setDBColumnName("NAME");
		addColumn(colName);
		
		//Setting properties for type
		Column colType = new Column("Type");
		colType.setMethodName("getTypeDescription");			   
		colType.setColumnWidth("47%");
		colType.setIsHeaderLink(true);
		colType.setHeaderLinkTitle("Sort by Type");
		colType.setDBColumnName("TPA_GENERAL_CODE.DESCRIPTION");
		addColumn(colType); 
		
		//Setting properties for image		
		/*Column colImage = new Column("Image");				
		colImage.setWidth(40);		
		colType.setDBColumnName("PKG_SEQ_ID");
		addColumn(colImage);*/
		
		//Setting properties for check box
		Column colSelect = new Column("Select");
		colSelect.setComponentType("checkbox");
		colSelect.setComponentName("chkopt");
		colSelect.setColumnWidth("5%");
		addColumn(colSelect);		
	}//end of public void setTableProperties()	
}//end of class TariffItemTable