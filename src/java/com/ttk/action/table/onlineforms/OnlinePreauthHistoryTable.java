/**
 * @ (#) OnlinePreauthHistoryTable.java 23rd July 2007
 * Project      : TTK HealthCare Services
 * File         : OnlinePreauthHistoryTable.java
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
 *  This class provides the information of Preauth History  details
 *
 */
public class OnlinePreauthHistoryTable extends Table{

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for History
        Column colpreAuthNo = new Column("Cashless No.");
        colpreAuthNo.setMethodName("getPreAuthNo");
        colpreAuthNo.setColumnWidth("14%");
        colpreAuthNo.setIsHeaderLink(true);
        colpreAuthNo.setHeaderLinkTitle("Sort by: Cashless No.");
        colpreAuthNo.setIsLink(true);
        colpreAuthNo.setLinkTitle("View History");
        colpreAuthNo.setDBColumnName("PRE_AUTH_NUMBER");
        addColumn(colpreAuthNo);

        //Setting properties for Hospital Name
        Column colHospName =new Column("Hospital Name");
        colHospName.setMethodName("getHospitalName");
        colHospName.setColumnWidth("15%");
        colHospName.setIsHeaderLink(true);
        colHospName.setHeaderLinkTitle("Sort by: Hospital Name");
        colHospName.setDBColumnName("HOSP_NAME");
        addColumn(colHospName);

        //Setting properties for Approved Amt. (Rs)
        Column colAppAmt =new Column("Approved Amt. (Rs)");
        colAppAmt.setMethodName("getApprovedAmount");
        colAppAmt.setColumnWidth("10%");
        colAppAmt.setIsHeaderLink(true);
        colAppAmt.setHeaderLinkTitle("Sort by: Approved Amt. (Rs)");
        colAppAmt.setDBColumnName("TOTAL_APP_AMOUNT");
        addColumn(colAppAmt);
        
        //Setting properties for Ailment
        Column colAilment =new Column("Ailment");
        colAilment.setMethodName("getAilement");
        colAilment.setColumnWidth("10%");
        colAilment.setIsHeaderLink(true);
        colAilment.setHeaderLinkTitle("Sort by: Ailment");
        colAilment.setDBColumnName("PROVISIONAL_DIAGNOSIS");
        addColumn(colAilment);

        //Setting properties for Admission date
        Column colAdmDate =new Column("Admission Date / Time");
        colAdmDate.setMethodName("getPreuthAdmissionDate");
        colAdmDate.setColumnWidth("22%");
        colAdmDate.setIsHeaderLink(true);
        colAdmDate.setHeaderLinkTitle("Sort by: Admission Date / Time");
        colAdmDate.setDBColumnName("LIKELY_DATE_OF_HOSPITALIZATION");
        addColumn(colAdmDate);

        //Setting properties for Received Date / Time
        Column colRecDate =new Column("Received Date / Time");
        colRecDate.setMethodName("getPreAuthReceivedDate");
        colRecDate.setColumnWidth("21%");
        colRecDate.setIsHeaderLink(true);
        colRecDate.setHeaderLinkTitle("Sort by: Received Date / Time");
        colRecDate.setDBColumnName("PAT_RECEIVED_DATE");
        addColumn(colRecDate);

        //Setting properties for Status
        Column colStatus =new Column("Status");
        colStatus.setMethodName("getStatusDesc");
        colStatus.setColumnWidth("8%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("DESCRIPTION");
        addColumn(colStatus);

    }//end of setTableProperties()

}//end of pre-auth HistoryTable
