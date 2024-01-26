
/**
* @ (#) GuaranteeDetailsTable.java Jun 23, 2006
* Project 		: TTK HealthCare Services
* File			: GuaranteeDetailsTable.java
* Author 		: Arun K.M
* Company 		: Span Systems Corporation
* Date Created  : Jun 23, 2006
*
* @author 		: Arun K.M
* Modified by 	:
* Modified date :
* Reason :
*/
package com.ttk.action.table.finance;


import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class GuaranteeDetailsTable extends Table {


    public void setTableProperties() {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Ref. No.
        Column colRefNo = new Column("Banker Name ");
        colRefNo.setMethodName("getBankerName");
        colRefNo.setColumnWidth("20%");
        colRefNo.setIsLink(true);
        colRefNo.setLinkTitle("Edit:Banker Name");
        colRefNo.setIsHeaderLink(true);
        colRefNo.setHeaderLinkTitle("Sort by: Banker Name");
        colRefNo.setDBColumnName("BANKER_NAME");
        addColumn(colRefNo);

        //Setting properties for Buffer Date
        Column colBuffDate = new Column("Bank Guarantee Amt.");
        colBuffDate.setMethodName("getBanGuarAmt");
        colBuffDate.setColumnWidth("30%");
        colBuffDate.setIsHeaderLink(true);
        colBuffDate.setHeaderLinkTitle("Sort by Bank Guarantee Amt.");
        colBuffDate.setDBColumnName("BANK_GUARANTEED");
        addColumn(colBuffDate);

        //Setting properties for Buffer Amt. (Rs)
        Column colBuffAmt = new Column("From Date ");
        colBuffAmt.setMethodName("getBankFromDate");
        colBuffAmt.setColumnWidth("15%");
        colBuffAmt.setIsHeaderLink(true);
        colBuffAmt.setHeaderLinkTitle("Sort by From Date");
        colBuffAmt.setDBColumnName("BANKER_FROM_DATE");
        addColumn(colBuffAmt);


        //Setting properties for Mode
        Column colMode = new Column("To Date ");
        colMode.setMethodName("getBankToDate");
        colMode.setColumnWidth("15%");
        colMode.setIsHeaderLink(true);
        colMode.setHeaderLinkTitle("Sort by To Date");
        colMode.setDBColumnName("BANKER_TO_DATE");
        addColumn(colMode);
        
        //Setting properties for BG Type
        Column colBgType = new Column("BG Type ");
        colBgType.setMethodName("getBgType");
        colBgType.setColumnWidth("10%");
        colBgType.setIsHeaderLink(true);
        colBgType.setHeaderLinkTitle("Sort by BG Type");
        colBgType.setDBColumnName("BANK_GUARANTEE_TYPE");
        addColumn(colBgType);
        
        //Setting properties for BG Status
        Column colBgStatus = new Column("BG Status ");
        colBgStatus.setMethodName("getBgStatus");
        colBgStatus.setColumnWidth("10%");
        colBgStatus.setIsHeaderLink(true);
        colBgStatus.setHeaderLinkTitle("Sort by BG Status");
        colBgStatus.setDBColumnName("BANK_GUARANTEE_STATUS");
        addColumn(colBgStatus);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }

}

