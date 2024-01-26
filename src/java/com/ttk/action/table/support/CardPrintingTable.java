/**
 * @ (#) CardPrintingTable.java Feb 15, 2006
 * Project       : TTK HealthCare Services
 * File          : CardPrintingTable.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Feb 15, 2006
 * @author       : Bhaskar Sandra
 *
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.support;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * this class provides the information of Card Batch table
 */
public class CardPrintingTable extends Table {
     /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties() {
        setRowCount(10);
         setCurrentPage(1);
        setPageLinkCount(10);

        Column colBatch=new Column("Batch No.");
        colBatch.setMethodName("getBatchNbr");
        colBatch.setColumnWidth("47%");
        colBatch.setIsLink(true);
        colBatch.setLinkTitle("Batch");
        colBatch.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
        colBatch.setHeaderLinkTitle("Sort by: Batch");  // Set the header link title
        colBatch.setDBColumnName("BATCH_NO");
        addColumn(colBatch);

        Column colTTKBranch=new Column("Vidal Health Branch");
        colTTKBranch.setMethodName("getCompanyName");
        colTTKBranch.setColumnWidth("27%");
        colTTKBranch.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
        colTTKBranch.setHeaderLinkTitle("Sort by: Vidal Health Branch");  // Set the header link title
        colTTKBranch.setDBColumnName("OFFICE_NAME");
        addColumn(colTTKBranch);

        Column colDate=new Column("Batch Date");
        colDate.setMethodName("getCardBatchDate");
        colDate.setColumnWidth("18%");
        colDate.setIsHeaderLink(true);                    // Is this column header contains the hyperlink
        colDate.setHeaderLinkTitle("Sort by: Batch Date");  // Set the header link title
        colDate.setDBColumnName("BATCH_DATE");
        addColumn(colDate);

        //Setting properties for image Assighn
        Column colImage3 = new Column("");
        colImage3.setIsImage(true);
        colImage3.setIsImageLink(true);
        colImage3.setImageName("getCardDetailImageName");
        colImage3.setImageTitle("getCardDetailImageTitle");
        addColumn(colImage3);

//      Setting properties for image send mail
        Column colImage4 = new Column("");
        colImage4.setIsImage(true);
        colImage4.setIsImageLink(true);
        colImage4.setImageName("getSendMailName");
        colImage4.setImageTitle("getSendMailTitle");
        addColumn(colImage4);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }//end of void setTableProperties()

}
