/**
 * @ (#) OnlineAssistanceTable.java Oct 23, 2008
 * Project      : TTK HealthCare Services
 * File         : OnlineAssistanceTable.java
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : Oct 23, 2008
 *
 * @author       : Balakrishna Erram
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.support;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Investigation details
 *
 */
public class OnlineAssistanceTable extends Table{

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Intemation No.
        Column colReqNo = new Column("Request No.");
        colReqNo.setMethodName("getRequestID");
        colReqNo.setColumnWidth("10%");
        colReqNo.setIsHeaderLink(true);
        colReqNo.setHeaderLinkTitle("Sort by: Request No.");
        colReqNo.setIsLink(true);
        colReqNo.setLinkTitle("Edit Request No.");
        colReqNo.setDBColumnName("REQUEST_ID");
        addColumn(colReqNo);

        //Setting properties for Scheme No.
        Column colPolicyNo =new Column("Scheme No.");
        colPolicyNo.setMethodName("getPolicyNbr");
        colPolicyNo.setColumnWidth("20%");
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setHeaderLinkTitle("Sort by: Scheme No.");
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);

        //Setting properties for Vidal Health TPA ID
        Column colEnrollID =new Column("Enrollment No.");
        colEnrollID.setMethodName("getEnrollmentNbr");
        colEnrollID.setColumnWidth("18%");
        colEnrollID.setIsHeaderLink(true);
        colEnrollID.setHeaderLinkTitle("Sort by: Vidal Healthcare ID");
        colEnrollID.setDBColumnName("TPA_ENROLLMENT_NUMBER");
        addColumn(colEnrollID);
        
        //Setting properties for Member Name
        Column colMemberName =new Column("Employee Name");
        colMemberName.setMethodName("getEmpname");
        colMemberName.setColumnWidth("20%");
        colMemberName.setIsHeaderLink(true);
        colMemberName.setHeaderLinkTitle("Sort by: Employee Name");
        colMemberName.setDBColumnName("BENEFICIARY_NAME");
        addColumn(colMemberName);

        //Setting properties for Status
        Column colStatus =new Column("Status");
        colStatus.setMethodName("getStatus");
        colStatus.setColumnWidth("12%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("STATUS");
        addColumn(colStatus);

        Column colFeedBackResponse = new Column("Vidal Healthcare Response");
        colFeedBackResponse.setMethodName("getFeedBackResponse");
        colFeedBackResponse.setColumnWidth("20%");
        colFeedBackResponse.setIsHeaderLink(true);
        colFeedBackResponse.setHeaderLinkTitle("Sort by: Vidal Healthcare Response");
        colFeedBackResponse.setDBColumnName("FEEDBACK_RESPONSE");
        addColumn(colFeedBackResponse);

        //Setting properties for Responded Date/Time
       /* Column colRespondedDate = new Column("Responded Date/Time");
        colRespondedDate.setMethodName("getTTKRespondedDate");
        colRespondedDate.setColumnWidth("14%");
        colRespondedDate.setIsHeaderLink(true);
        colRespondedDate.setHeaderLinkTitle("Sort by: Responded Date/Time");
        colRespondedDate.setDBColumnName("CLARIFIED_DATE");
        addColumn(colRespondedDate);         */

    }//end of setTableProperties()

}//end of InvestigationTable
