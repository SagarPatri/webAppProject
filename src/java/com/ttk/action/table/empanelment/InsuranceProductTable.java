/**
 * @ (#)  InsuranceProductTable.java Nov 14, 2005
 * Project      : TTKPROJECT
 * File         : InsuranceProductTable.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Nov 14, 2005
 *
 * @author       :  Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of Insurance Associate Product Details table
 *
 */
public class InsuranceProductTable extends Table {

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	public void setTableProperties() {
		setRowCount(20);
		setCurrentPage(1);
		setPageLinkCount(20);

		//Setting properties for Product Name
        Column colProductName = new Column("Product Name");
        colProductName.setMethodName("getProductName");
        colProductName.setColumnWidth("28%");
        colProductName.setIsHeaderLink(true);
        colProductName.setIsLink(true);
        colProductName.setLinkTitle("Edit");
        colProductName.setDBColumnName("PRODUCT_NAME");
        colProductName.setHeaderLinkTitle("Sort by: Product Name");
        addColumn(colProductName);

        //Setting properties for Commission (%)
        Column colCommission = new Column("Service Charges (%)");
        colCommission.setMethodName("getCommissionPercentage");
        colCommission.setColumnWidth("22%");
        colCommission.setIsHeaderLink(true);
        colCommission.setDBColumnName("COMMISSION_TO_TPA");
        colCommission.setHeaderLinkTitle("Sort by: Service Charges (%)");
        addColumn(colCommission);

        //Setting properties for Enrollment Type
        Column colEnrollType = new Column("Enrollment Type");
        colEnrollType.setMethodName("getEnrollmentDesc");
        colEnrollType.setColumnWidth("21%");
        colEnrollType.setIsHeaderLink(true);
        colEnrollType.setDBColumnName("ENROL_DESCRIPTION");
        colEnrollType.setHeaderLinkTitle("Sort by: Enrollment Type");
        addColumn(colEnrollType);

        //Setting properties for Start Date
        Column colStartDate = new Column("Start Date");
        colStartDate.setMethodName("getFormattedStartDate");
        colStartDate.setColumnWidth("12%");
        colStartDate.setIsHeaderLink(true);
        colStartDate.setDBColumnName("VALID_FROM");
        colStartDate.setHeaderLinkTitle("Sort by: Start Date");
        addColumn(colStartDate);

        //Setting properties for End Date
        Column colEndDate = new Column("End Date");
        colEndDate.setMethodName("getFormattedEndDate");
        colEndDate.setColumnWidth("12%");
        colEndDate.setIsHeaderLink(true);
        colEndDate.setDBColumnName("VALID_TO");
        colEndDate.setHeaderLinkTitle("Sort by: End Date");
        addColumn(colEndDate);

        //Setting properties for check box
    	Column colSelect = new Column("Select");
    	colSelect.setComponentType("checkbox");
    	colSelect.setComponentName("chkopt");
    	addColumn(colSelect);

	}// End of setTableProperties()

}// End of InsuranceProductTable
