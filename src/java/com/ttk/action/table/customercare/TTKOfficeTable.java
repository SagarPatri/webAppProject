/**
 * @ (#) TTKOfficeTable.java Nov 15, 2006
 * Project       : Vidal Health TPA  Services
 * File          : TTKOfficeTable.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Nov 15, 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.customercare;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of sbb_vendor table
 */

public class TTKOfficeTable extends Table // implements TableObjectInterface,Serializable
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
		Column colName = new Column("Vidal Healthcare Office Code");
		colName.setMethodName("getOfficeCode");
		colName.setColumnWidth("47%");
		colName.setIsLink(true);
		colName.setLinkTitle("View Office Detail");
		colName.setIsHeaderLink(true);
		colName.setHeaderLinkTitle("Sort by : Vidal Healthcare Office Code");
		colName.setDBColumnName("OFFICE_CODE");
		addColumn(colName);

		//Setting properties for type
		Column colType = new Column("Vidal Healthcare Office Name");
		colType.setMethodName("getOfficeName");
		colType.setColumnWidth("53%");
		colType.setIsHeaderLink(true);
		colType.setHeaderLinkTitle("Sort by : Vidal Healthcare Office Name");
		colType.setDBColumnName("OFFICE_NAME");
		addColumn(colType);
	}//end of public void setTableProperties()
}//end of class TTKOfficeTable