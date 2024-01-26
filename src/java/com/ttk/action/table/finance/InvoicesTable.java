/**
 * @ (#) InvoicesTable.java 26 Oct 2007
 * Project       : TTK HealthCare Services
 * File          : InvoicesTable.java
 * Author        : Yogesh S.C
 * Company       : Span Systems Corporation
 * Date Created  : 26 Oct 2007
 *
 * @author       : Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Invoice details
 *
 */

public class InvoicesTable extends Table{

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	public void setTableProperties() {
		
		setRowCount(10000);
        setCurrentPage(1);
        setPageLinkCount(1);
       //Setting properties for Invoice No.
        Column colInvoiceNo = new Column("Invoice No.");
        colInvoiceNo.setMethodName("getInvoiceNbr");
        colInvoiceNo.setColumnWidth("23%");
        colInvoiceNo.setIsLink(true);
        colInvoiceNo.setLinkTitle("Edit Invoice");
        colInvoiceNo.setIsHeaderLink(true);
        colInvoiceNo.setHeaderLinkTitle("Sort by: Invoice No.");
        colInvoiceNo.setDBColumnName("INVOICE_NUMBER");
        addColumn(colInvoiceNo);

        //Setting properties for Invoice Date From
        Column colInvoiceDateFrom  =new Column("Invoice Date From");
        colInvoiceDateFrom.setMethodName("getInvFromDate");
        colInvoiceDateFrom.setColumnWidth("23%");
        colInvoiceDateFrom.setIsHeaderLink(true);
        colInvoiceDateFrom.setHeaderLinkTitle("Sort by: Invoice Date From");
        colInvoiceDateFrom.setDBColumnName("INV_FROM_DATE");
        addColumn(colInvoiceDateFrom);

        //Setting properties for Invoice Date To
        Column colInvoiceDateTo =new Column("Invoice Date To");
        colInvoiceDateTo.setMethodName("getInvToDate");        
        colInvoiceDateTo.setColumnWidth("23%");
        colInvoiceDateTo.setIsHeaderLink(true);
        colInvoiceDateTo.setHeaderLinkTitle("Sort by: Invoice Date To");
        colInvoiceDateTo.setDBColumnName("INV_TO_DATE");
        addColumn(colInvoiceDateTo);       

        //Setting properties for Status
        Column colStatus =new Column("Status");
        colStatus.setMethodName("getStatusDesc");
        colStatus.setColumnWidth("13%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("STATUS");
        addColumn(colStatus);
        
        //Setting properties for No. of Policies
        Column colNoOfPolicies =new Column("No. of Schemes");
        colNoOfPolicies.setMethodName("getPolicyCount");       
        colNoOfPolicies.setColumnWidth("18%");
        colNoOfPolicies.setIsHeaderLink(true);
        colNoOfPolicies.setHeaderLinkTitle("Sort by: No. of Schemes");
        colNoOfPolicies.setDBColumnName("NUMOFPOLICIES");
        addColumn(colNoOfPolicies);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setVisibility(false);
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
		
	}//end of setTableProperties()

}//end of class InvoicesTable
