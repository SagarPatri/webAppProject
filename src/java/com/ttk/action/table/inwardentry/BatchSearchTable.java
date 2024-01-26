/**
 * @ (#) BatchSearchTable.java 2nd Feb 2006
 * Project      : TTK HealthCare Services
 * File         : BatchSearchTable.java
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : 2nd Feb 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.inwardentry;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Product details
 *
 */
public class BatchSearchTable extends Table{

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Batch No
        Column colBatchNo = new Column("Batch No.");
        colBatchNo.setMethodName("getBatchNbr");
        colBatchNo.setColumnWidth("15%");
        colBatchNo.setIsHeaderLink(true);
        colBatchNo.setHeaderLinkTitle("Sort by: Batch No.");
        colBatchNo.setIsLink(true);
        colBatchNo.setLinkTitle("Edit Batch");
        colBatchNo.setDBColumnName("BATCH_NUMBER");
        addColumn(colBatchNo);

        //Setting properties for Received Date
        Column colReceivedDate = new Column("Received Date");
        colReceivedDate.setMethodName("getBatchRecdDate");
        colReceivedDate.setColumnWidth("15%");
        colReceivedDate.setIsHeaderLink(true);
        colReceivedDate.setHeaderLinkTitle("Sort by: Received Date");
        colReceivedDate.setDBColumnName("RECEIVED_DATE");
        addColumn(colReceivedDate);

        //Setting properties for No. of Policies
        Column colNoOfPolicies =new Column("No. of Policies");
        colNoOfPolicies.setMethodName("getNbrOfPolicies");
        colNoOfPolicies.setColumnWidth("15%");
        colNoOfPolicies.setIsHeaderLink(true);
        colNoOfPolicies.setHeaderLinkTitle("Sort by: No. of Policies");
        colNoOfPolicies.setDBColumnName("NO_OF_POLICIES");
        addColumn(colNoOfPolicies);

        //Setting properties for Insurance company name
        Column colInsCompany =new Column("Insurance Company");
        colInsCompany.setMethodName("getCompanyName");
        colInsCompany.setColumnWidth("20%");
        colInsCompany.setIsHeaderLink(true);
        colInsCompany.setHeaderLinkTitle("Sort by: Healthcare Company");
        colInsCompany.setDBColumnName("INS_COMP_NAME");
        addColumn(colInsCompany);

        //Setting properties for Office Code
        Column colOfficeCode =new Column("Insurance Code");
        colOfficeCode.setMethodName("getOfficeCode");
        colOfficeCode.setColumnWidth("15%");
        colOfficeCode.setIsHeaderLink(true);
        colOfficeCode.setHeaderLinkTitle("Sort by: Company Code");
        colOfficeCode.setDBColumnName("INS_COMP_CODE_NUMBER");
        addColumn(colOfficeCode);

        //Setting properties for Clarification Status
        Column colClarificationStatus =new Column("Clarification Status");
        colClarificationStatus.setMethodName("getClarifyStatusDesc");
        colClarificationStatus.setColumnWidth("20%");
        colClarificationStatus.setIsHeaderLink(true);
        colClarificationStatus.setHeaderLinkTitle("Sort by: Clarification Status");
        colClarificationStatus.setDBColumnName("INS_COMP_CODE_NUMBER");
        addColumn(colClarificationStatus);


        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }//end of setTableProperties()

}//end of BatchSearchTable