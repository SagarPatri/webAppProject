/**
 * @ (#) ClaimTable.java 4th Aug 2006
 * Project      : TTK HealthCare Services
 * File         : ClaimTable.java
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : 4th Aug 2006
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
 *  This class provides the information of Claim details
 *
 */
public class ClaimTable extends Table{

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
        Column colpreAuthNo = new Column("Claim No.");
        colpreAuthNo.setMethodName("getClaimNo");
        colpreAuthNo.setColumnWidth("18%");
        colpreAuthNo.setIsHeaderLink(true);
        colpreAuthNo.setHeaderLinkTitle("Sort by: Claim No.");
        colpreAuthNo.setIsLink(true);
        colpreAuthNo.setLinkTitle("View History");
        colpreAuthNo.setDBColumnName("CLAIM_NUMBER");
        addColumn(colpreAuthNo);
        
        //Setting properties for Policy Number
        Column colPolicyno =new Column("Scheme No.");
        colPolicyno.setMethodName("getPolicyNo");
        colPolicyno.setColumnWidth("15%");
        colPolicyno.setIsHeaderLink(true);
        colPolicyno.setHeaderLinkTitle("Sort by: Scheme No.");
        colPolicyno.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyno);
        //Setting properties for Authorization No
        /*Column colAuthorization = new Column("Settlement No.");
        colAuthorization.setMethodName("getAuthorizationNO");
        colAuthorization.setColumnWidth("17%");
        colAuthorization.setIsHeaderLink(true);
        colAuthorization.setHeaderLinkTitle("Sort by: Settlement No.");
        colAuthorization.setDBColumnName("AUTH_NUMBER");
        addColumn(colAuthorization);*/

        //Setting properties for Hospital Name
        Column colHospName =new Column("Member Name");
        colHospName.setMethodName("getClaimantName");
        colHospName.setColumnWidth("15%");
        colHospName.setIsHeaderLink(true);
        colHospName.setHeaderLinkTitle("Sort by: Member Name");
        colHospName.setDBColumnName("CLAIMANT_NAME");
        addColumn(colHospName);

        //Setting properties for Approved Amt. (Rs)
        Column colAppAmt =new Column("Settlement Amt. (Rs)");
        colAppAmt.setMethodName("getApprovedAmount");
        colAppAmt.setColumnWidth("10%");
        colAppAmt.setIsHeaderLink(true);
        colAppAmt.setHeaderLinkTitle("Sort by: Settlement Amt. (Rs)");
        colAppAmt.setDBColumnName("TOTAL_APP_AMOUNT");
        addColumn(colAppAmt);

        /*//Setting properties for Received Date / Time
        Column colRecDate =new Column("Received Date / Time");
        colRecDate.setMethodName("getPreAuthReceivedDate");
        colRecDate.setColumnWidth("21%");
        colRecDate.setIsHeaderLink(true);
        colRecDate.setHeaderLinkTitle("Sort by: Received Date / Time");
        colRecDate.setDBColumnName("RCVD_DATE");
        addColumn(colRecDate);*/
        
        //Setting properties for Admission Date / Time
        Column colAdmDate =new Column("Admission Date / Time");
        colAdmDate.setMethodName("getClaimAdmissionDateTime");
        colAdmDate.setColumnWidth("17%");
        colAdmDate.setIsHeaderLink(true);
        colAdmDate.setHeaderLinkTitle("Sort by: Admission Date / Time");
        colAdmDate.setDBColumnName("DATE_OF_ADMISSION");
        addColumn(colAdmDate);
        
        //Setting properties for Discharge Date / Time
        Column colDischargeDate =new Column("Discharge Date / Time");
        colDischargeDate.setMethodName("getClmDischargeDateTime");
        colDischargeDate.setColumnWidth("17%");
        colDischargeDate.setIsHeaderLink(true);
        colDischargeDate.setHeaderLinkTitle("Sort by: Discharge Date / Time");
        colDischargeDate.setDBColumnName("DATE_OF_DISCHARGE");
        addColumn(colDischargeDate);
        
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
