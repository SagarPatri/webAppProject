/**
 * @ (#) CardRulesTable.java Nov 08, 2005
 * Project       : TTK HealthCare Services
 * File          : CardRulesTable.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Nov 08, 2005
 *
 * @author       : Bhaskar Sandra
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class CardRulesTable extends Table {
	/**
	 * This creates the columnproperties objects for each and
	 * every column and adds the column object to the table
	 */

	public void setTableProperties()
	{
	setRowCount(10);
	setCurrentPage(1);
	setPageLinkCount(10);
	Column columnRevision =new Column("Office Code ");           // Creating Column object for Revision column
	columnRevision.setMethodName("getOfficeCode");                        // Set the method name
	columnRevision.setColumnWidth("30%");                    // Set the column width to be displayed in Grid
	columnRevision.setIsLink(true);                          // Is this column data contains  the hyperlink
	columnRevision.setLinkTitle("Office Code");                 // Set the link title
	columnRevision.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
	columnRevision.setHeaderLinkTitle("Sort by: Office Code");  // Set the header link title
	columnRevision.setDBColumnName("ABBREVATION_CODE || '-' || INS_COMP_CODE_NUMBER");                      // Set the Database column name for this column
	addColumn(columnRevision);

	}


}
