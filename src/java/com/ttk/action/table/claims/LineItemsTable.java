/**
 * @ (#)  LineItemsTable.java July 19,2006
 * Project      : TTK HealthCare Services
 * File         : LineItemsTable.java
 * Author       : Harsha Vardhan B N
 * Company      : Span Systems Corporation
 * Date Created : July 19,2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class LineItemsTable extends Table {
	/**
	 * This creates the columnproperties objects for each and
	 * every column and adds the column object to the table
	 */

		public void setTableProperties()
		{
			setRowCount(50);
			setCurrentPage(1);
			setPageLinkCount(10);

			//Setting properties for Description
			Column colDesc = new Column("Description");
			colDesc.setMethodName("getLineItemNbr");
			colDesc.setColumnWidth("29%");
			colDesc.setIsHeaderLink(true);
			colDesc.setHeaderLinkTitle("Sort by Description");
			colDesc.setDBColumnName("DESCRIPTION");
			addColumn(colDesc);

			//Setting properties for Account Head
			Column colAccountHead = new Column("Account Head ");
			colAccountHead.setMethodName("getAccountHeadDesc");
			colAccountHead.setColumnWidth("28%");
			colAccountHead.setIsLink(true);
			colAccountHead.setLinkTitle("Edit AccountHead");
			colAccountHead.setIsHeaderLink(true);
			colAccountHead.setHeaderLinkTitle("Sort by Account Head ");
			colAccountHead.setDBColumnName("WARD_DESCRIPTION");
			addColumn(colAccountHead);

			//Setting properties for Requested Amt. (Rs)
			Column colReqAmt = new Column("Requested Amt. (Rs)");
			colReqAmt.setMethodName("getRequestedAmt");
			colReqAmt.setColumnWidth("22%");
			colReqAmt.setIsHeaderLink(true);
			colReqAmt.setHeaderLinkTitle("Sort by Requested Amt. (Rs)");
			colReqAmt.setDBColumnName("REQUESTED_AMOUNT");
			addColumn(colReqAmt);

			//Setting properties for Allowed Amt. (Rs)
			Column colAllowedAmt = new Column("Allowed Amt. (Rs)");
			colAllowedAmt.setMethodName("getAllowedAmt");
			colAllowedAmt.setColumnWidth("21%");
			colAllowedAmt.setIsHeaderLink(true);
			colAllowedAmt.setHeaderLinkTitle("Sort by Allowed Amt. (Rs)");
			colAllowedAmt.setDBColumnName("ALLOWED_AMOUNT");
			addColumn(colAllowedAmt);

			//Setting properties for check box
			Column colSelect = new Column("Select");
			colSelect.setComponentType("checkbox");
			colSelect.setComponentName("chkopt");
			addColumn(colSelect);
		}//end of public void setTableProperties()
}//end of class LineItemsTable