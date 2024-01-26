/**
 * @ (#) SupportDocTable.java May 06, 2006
 * Project      : TTK HealthCare Services
 * File         : SupportDocTable.java
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : May 06, 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * this class provides the information of support document table
 */
public class SupportDocTable extends Table
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

        //Setting properties for shortfall no.0
        Column colShortFallNo = new Column("Shortfall No.");
        colShortFallNo.setMethodName("getInvShortfallNo");
        colShortFallNo.setColumnWidth("30%");
        colShortFallNo.setIsLink(true);
        colShortFallNo.setIsHeaderLink(true);
        colShortFallNo.setLinkTitle("Edit ShortFall");
        colShortFallNo.setHeaderLinkTitle("Sort by: Shortfall No.");
        colShortFallNo.setDBColumnName("ID");
        addColumn(colShortFallNo);

        //Setting properties for shortfall type1
        Column colShortFallType = new Column("Shortfall Type");
        colShortFallType.setMethodName("getShortfallDesc");
        //colShortFallType.setIsLink(true);
        colShortFallType.setColumnWidth("20%");
        colShortFallType.setIsHeaderLink(true);
        colShortFallType.setHeaderLinkTitle("Sort by: Shortfall Type");
        colShortFallType.setDBColumnName("TYPE");
        addColumn(colShortFallType);

        //Setting properties for investigation no.2
        Column colInvestigationNo = new Column("Investigation No.");
        colInvestigationNo.setMethodName("getInvShortfallNo");
        colInvestigationNo.setColumnWidth("30%");
        colInvestigationNo.setIsLink(true);
        colInvestigationNo.setIsHeaderLink(true);
        colInvestigationNo.setHeaderLinkTitle("Sort by: Investigation No.");
        colInvestigationNo.setLinkTitle("Edit Investigation");
        colInvestigationNo.setDBColumnName("ID");
        addColumn(colInvestigationNo);

        //Setting properties for marked date3
        Column colMarkedDate = new Column("Marked Date");
        colMarkedDate.setMethodName("getDocumentDateTime");
        colMarkedDate.setColumnWidth("20%");
        colMarkedDate.setIsHeaderLink(true);
        colMarkedDate.setHeaderLinkTitle("Sort by: Marked Date");
        colMarkedDate.setDBColumnName("DATE_TIME");
        addColumn(colMarkedDate);

        //Setting properties for investigated by4
        Column colInvestigatedBy = new Column("Investigated By");
        colInvestigatedBy.setMethodName("getInvestigatedBy");
        colInvestigatedBy.setColumnWidth("30%");
        colInvestigatedBy.setIsHeaderLink(true);
        colInvestigatedBy.setHeaderLinkTitle("Sort by: Investigated By");
        colInvestigatedBy.setDBColumnName("INVESTIGATED_BY");
        addColumn(colInvestigatedBy);

        //Setting properties for Ref. No.5
        Column colRefNo = new Column("Ref. No.");
        colRefNo.setMethodName("getRefNbr");
        colRefNo.setColumnWidth("50%");
        colRefNo.setIsLink(true);
        colRefNo.setIsHeaderLink(true);
        colRefNo.setLinkTitle("Edit Discharge Voucher");
        colRefNo.setHeaderLinkTitle("Sort by: Ref. No.");
        colRefNo.setDBColumnName("ID");
        addColumn(colRefNo);

        //Setting properties for status6
        Column colStatus = new Column("Status");
        colStatus.setMethodName("getStatusDesc");
        colStatus.setColumnWidth("20%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("STATUS");
        addColumn(colStatus);

        //Setting properties for date/time7
        Column colDateTime = new Column("Date / Time");
        colDateTime.setMethodName("getDocumentDateTime");
        colDateTime.setColumnWidth("30%");
        colDateTime.setIsHeaderLink(true);
        colDateTime.setHeaderLinkTitle("Sort by: Date / Time");
        colDateTime.setDBColumnName("DATE_TIME");
        addColumn(colDateTime);

        //Setting the properties for buffer request no.8
        Column colRequestNoe = new Column("Buffer Request No.");
        colRequestNoe.setMethodName("getBufferNbr");
        colRequestNoe.setColumnWidth("20%");
        colRequestNoe.setIsHeaderLink(true);
        colRequestNoe.setIsLink(true);
        colRequestNoe.setHeaderLinkTitle("Sort by: Buffer Request No.");
        colRequestNoe.setDBColumnName("ID");
        addColumn(colRequestNoe);
        
        
        //added for hyundai buffer9
        //Setting the properties for Claim Type
        Column colClaimType = new Column("Claim Type");
        colClaimType.setMethodName("getClaimType");
        colClaimType.setColumnWidth("20%");
       // colBufferType.setIsHeaderLink(true);
      //  colClaimType.setIsLink(true);
        colClaimType.setHeaderLinkTitle("Sort by: Buffer Type");
        colClaimType.setDBColumnName("getBufferType");
        addColumn(colClaimType); 
        
        
        //Setting the properties for Buffer Type
        Column colBufferType = new Column("Buffer Type");
        colBufferType.setMethodName("getBufferType");
        colBufferType.setColumnWidth("20%");
       // colBufferType.setIsHeaderLink(true);
      //  colBufferType.setIsLink(true);
        colBufferType.setHeaderLinkTitle("Sort by: Buffer Type");
        colBufferType.setDBColumnName("getBufferType");
        addColumn(colBufferType);
        // end added for hyundai buffer
        //Setting properties for requested date/time10
        Column colRequestedDateTime = new Column("Requested Date / Time");
        colRequestedDateTime.setMethodName("getRequestedDateTime");
        colRequestedDateTime.setColumnWidth("25%");
        colRequestedDateTime.setIsHeaderLink(true);
        colRequestedDateTime.setHeaderLinkTitle("Sort by: Requested Date / Time");
        colRequestedDateTime.setDBColumnName("BUFFER_REQ_DATE");
        addColumn(colRequestedDateTime);

        //Setting the properties for requested amount11
        Column colRequestedAmt = new Column("Requested Amt. (Rs)");
        colRequestedAmt.setMethodName("getRequestedAmt");
        colRequestedAmt.setColumnWidth("20%");
        colRequestedAmt.setIsHeaderLink(true);
        colRequestedAmt.setHeaderLinkTitle("Sort by: Requested Amt. (Rs)");
        colRequestedAmt.setDBColumnName("BUFFER_REQ_AMOUNT");
        addColumn(colRequestedAmt);

        //Setting properties for status12
        Column colBufferStatus = new Column("Status");
        colBufferStatus.setMethodName("getStatusDesc");
        colBufferStatus.setColumnWidth("15%");
        colBufferStatus.setIsHeaderLink(true);
        colBufferStatus.setHeaderLinkTitle("Sort by: Status");
        colBufferStatus.setDBColumnName("STATUS");
        addColumn(colBufferStatus);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }// end of public void setTableProperties()
}// end of SupportDocTable class