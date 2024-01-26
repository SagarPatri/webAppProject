/**
 * @ (#) InvestigationTable.java 6th May 2006
 * Project      : TTK HealthCare Services
 * File         : InvestigationTable.java
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : 6th May 2006
 *
 * @author       : Raghavendra T M
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
public class IntimationTable extends Table{

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
        Column colIntimNo = new Column("Intimation No.");
        colIntimNo.setMethodName("getIntimationNbr");
        colIntimNo.setColumnWidth("17%");
        colIntimNo.setIsHeaderLink(true);
        colIntimNo.setHeaderLinkTitle("Sort by: Intimation No.");
        colIntimNo.setIsLink(true);
        colIntimNo.setLinkTitle("Edit Intimation");
        colIntimNo.setDBColumnName("PAT_INTIMATION_ID");
        addColumn(colIntimNo);

        //Setting properties for Scheme No.
        Column colPolicyNo =new Column("Scheme No.");
        colPolicyNo.setMethodName("getPolicyNbr");
        colPolicyNo.setColumnWidth("20%");
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setHeaderLinkTitle("Sort by: Scheme No.");
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);

        //Setting properties forVidal Health TPAID
        Column colEnrollID =new Column("Vidal Healthcare ID");
        colEnrollID.setMethodName("getEnrollmentID");
        colEnrollID.setColumnWidth("20%");
        colEnrollID.setIsHeaderLink(true);
        colEnrollID.setHeaderLinkTitle("Sort by: Vidal Healthcare ID");
        colEnrollID.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollID);
        
        //Setting properties for Member Name
        Column colMemberName =new Column("Member Name");
        colMemberName.setMethodName("getMemName");
        colMemberName.setColumnWidth("20%");
        colMemberName.setIsHeaderLink(true);
        colMemberName.setHeaderLinkTitle("Sort by: Member Name");
        colMemberName.setDBColumnName("MEM_NAME");
        addColumn(colMemberName);

        //Setting properties for Status
        Column colStatus =new Column("Status");
        colStatus.setMethodName("getStatusDesc");
        colStatus.setColumnWidth("12%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("STATUS");
        addColumn(colStatus);

        //Setting properties for Responded Date/Time
        Column colRespondedDate = new Column("Responded Date/Time");
        colRespondedDate.setMethodName("getTTKRespondDate");
        colRespondedDate.setColumnWidth("11%");
        colRespondedDate.setIsHeaderLink(true);
        colRespondedDate.setHeaderLinkTitle("Sort by: Responded Date/Time");
        colRespondedDate.setDBColumnName("TPA_RESPONDED_DATE");
        addColumn(colRespondedDate);        

    }//end of setTableProperties()

}//end of InvestigationTable
