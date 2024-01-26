/**
 * @ (#) CardBatchTable.java July 24, 2008
 * Project       : TTK HealthCare Services
 * File          : CardBatchTable.java
 * Author        : Sanjay.G.Nayaka
 * Company       : Span Systems Corporation
 * Date Created  : July 24, 2008
 * @author       : Sanjay.G.Nayaka
 *
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.support;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of Card Batch  table
 */
public class CardBatchTable extends Table{
    
	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties() {
        setRowCount(50);
        setCurrentPage(1);
        setPageLinkCount(10);

        Column colCardBatchNo=new Column("Card Batch No.");
        colCardBatchNo.setMethodName("getBatchNbr");
        colCardBatchNo.setColumnWidth("27%");
        colCardBatchNo.setLinkTitle("CardBatchNo");
        colCardBatchNo.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
        colCardBatchNo.setHeaderLinkTitle("Sort by:Card Batch No");  // Set the header link title
        colCardBatchNo.setDBColumnName("BATCH_NO");
        addColumn(colCardBatchNo);

        Column colPolicyNo=new Column("Scheme No.");
        colPolicyNo.setMethodName("getPolicyNbr");
        colPolicyNo.setColumnWidth("27%");
        colPolicyNo.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
        colPolicyNo.setHeaderLinkTitle("Sort by: Scheme No");  // Set the header link title
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);

        Column colEnrollmentNo=new Column("Enrollment No.");
        colEnrollmentNo.setMethodName("getEnrollmentNbr");
        colEnrollmentNo.setColumnWidth("27%");
        colEnrollmentNo.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
        colEnrollmentNo.setHeaderLinkTitle("Sort by:Enrollment No");  // Set the header link title
        colEnrollmentNo.setDBColumnName("TPA_ENROLLMENT_NUMBER");
        addColumn(colEnrollmentNo);

        Column colBatchDate=new Column("Batch Date");
        colBatchDate.setMethodName("getCardBatchDate");
        colBatchDate.setColumnWidth("27%");
        colBatchDate.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
        colBatchDate.setHeaderLinkTitle("Sort by:Batch Date");  // Set the header link title
        colBatchDate.setDBColumnName("BATCH_DATE");
        addColumn(colBatchDate);

         //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }//end of void setTableProperties()

}//end of CardBatchTable
