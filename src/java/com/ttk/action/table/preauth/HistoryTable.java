/**
 * @ (#) HistoryTable.java 8th May 2006
 * Project      : TTK HealthCare Services
 * File         : HistoryTable.java
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : 8th May 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Investigation details
 *
 */
public class HistoryTable extends Table{

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
        colpreAuthNo.setColumnWidth("17%");
        colpreAuthNo.setIsHeaderLink(true);
        colpreAuthNo.setHeaderLinkTitle("Sort by: Cashless No.");
        colpreAuthNo.setIsLink(true);
        colpreAuthNo.setLinkTitle("View History");
        colpreAuthNo.setDBColumnName("PRE_AUTH_NUMBER");
        addColumn(colpreAuthNo);

        //Setting properties for Authorization No
        Column colAuthorization = new Column("Authorization No.");
        colAuthorization.setMethodName("getAuthorizationNO");
        colAuthorization.setColumnWidth("16%");
        colAuthorization.setIsHeaderLink(true);
        colAuthorization.setHeaderLinkTitle("Sort by: Authorization No.");
        colAuthorization.setDBColumnName("AUTH_NUMBER");
        addColumn(colAuthorization);

        //Setting properties for Hospital Name
        Column colHospName =new Column("Hospital Name");
        colHospName.setMethodName("getHospitalName");
        colHospName.setColumnWidth("16%");
        colHospName.setIsHeaderLink(true);
        colHospName.setHeaderLinkTitle("Sort by: Hospital Name");
        colHospName.setDBColumnName("HOSP_NAME");
        addColumn(colHospName);

        //Setting properties for Approved Amt. (Rs)
        Column colAppAmt =new Column("Approved Amt. (Rs)");
        colAppAmt.setMethodName("getApprovedAmount");
        colAppAmt.setColumnWidth("18%");
        colAppAmt.setIsHeaderLink(true);
        colAppAmt.setHeaderLinkTitle("Sort by: Approved Amt. (Rs)");
        colAppAmt.setDBColumnName("TOTAL_APP_AMOUNT");
        addColumn(colAppAmt);

        //Setting properties for Latest Enhanced
        Column colImage2 = new Column("Latest Enhanced");
        colImage2.setIsImage(true);
        colImage2.setImageName("getPreAuthImageName");
        colImage2.setImageTitle("getPreAuthImageTitle");
        colImage2.setColumnWidth("5%");
        addColumn(colImage2);  

        //Setting properties for Admission Date/Time
        Column colRecDate =new Column("Admission Date / Time");
        colRecDate.setMethodName("getClaimAdmissionDateTime");
        colRecDate.setColumnWidth("20%");
        colRecDate.setIsHeaderLink(true);
        colRecDate.setHeaderLinkTitle("Sort by: Admission Date / Time");
        colRecDate.setDBColumnName("LIKELY_DATE_OF_HOSPITALIZATION");
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

}//end of Cashless HistoryTable
