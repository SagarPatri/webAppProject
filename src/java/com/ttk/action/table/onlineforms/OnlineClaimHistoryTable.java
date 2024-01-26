/**
 * @ (#) OnlineClaimHistoryTable.java 23rd July 2007
 * Project      : TTK HealthCare Services
 * File         : OnlineClaimHistoryTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 23rd July 2007
 *
 * @author       : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Claim details
 *
 */
public class OnlineClaimHistoryTable extends Table{

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Claim No.
        Column colClaimNo = new Column("Claim No.");
        colClaimNo.setMethodName("getClaimNo");
        colClaimNo.setColumnWidth("18%");
        colClaimNo.setIsHeaderLink(true);
        colClaimNo.setHeaderLinkTitle("Sort by: Claim No.");
        colClaimNo.setIsLink(true);
        colClaimNo.setLinkTitle("View History");
        colClaimNo.setDBColumnName("CLAIM_NUMBER");
        addColumn(colClaimNo);

        //Setting properties for Member Name
        Column colClaimantName =new Column("Member Name");
        colClaimantName.setMethodName("getClaimantName");
        colClaimantName.setColumnWidth("15%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("CLAIMANT_NAME");
        addColumn(colClaimantName);

        //Setting properties for Settlement Amt. (Rs)
        Column colSettleAmt =new Column("Settlement Amt.(Rs)");
        colSettleAmt.setMethodName("getApprovedAmount");
        colSettleAmt.setColumnWidth("19%");
        colSettleAmt.setIsHeaderLink(true);
        colSettleAmt.setHeaderLinkTitle("Sort by: Settlement Amt.(Rs)");
        colSettleAmt.setDBColumnName("TOTAL_APP_AMOUNT");
        addColumn(colSettleAmt);
        
        //Setting properties for Ailment
        Column colAilment =new Column("Ailment");
        colAilment.setMethodName("getAilement");
        colAilment.setColumnWidth("15%");
        colAilment.setIsHeaderLink(true);
        colAilment.setHeaderLinkTitle("Sort by: Ailment");
        colAilment.setDBColumnName("PROVISIONAL_DIAGNOSIS");
        addColumn(colAilment);

        //Setting properties for Received Date / Time
        Column colRecDate =new Column("Claim Received Date / Time");
        colRecDate.setMethodName("getPreAuthReceivedDate");
        colRecDate.setColumnWidth("25%");
        colRecDate.setIsHeaderLink(true);
        colRecDate.setHeaderLinkTitle("Sort by: Claim Received Date / Time");
        colRecDate.setDBColumnName("RCVD_DATE");
        addColumn(colRecDate);

        //Setting properties for Status
        Column colStatus =new Column("Status");
        colStatus.setMethodName("getRemarks");
        colStatus.setColumnWidth("8%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("DESCRIPTION");
        addColumn(colStatus);

    }//end of setTableProperties()

}//end of ClaimTable
