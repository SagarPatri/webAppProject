/**
 * @ (#) BatchPolicyTable.java Feb 01, 2006
 * Project      : TTK HealthCare Services
 * File         : BatchPolicyTable.java
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : Feb 01, 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.inwardentry;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * this class provides the information of policy table
 */
public class BatchPolicyTable extends Table
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

        //Setting properties for Policy No.
        Column colPolicyNo = new Column("Policy No.");
        colPolicyNo.setMethodName("getPolicyNbr");
        colPolicyNo.setColumnWidth("20%");
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setImageName("getImageName");
        colPolicyNo.setImageTitle("getImageTitle");
        colPolicyNo.setLinkTitle("Edit Policy");
        colPolicyNo.setHeaderLinkTitle("Sort by: Policy No.");
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);

        //Setting properties for enrollment type
        Column colEnrollmentType = new Column("Policy Type");
        colEnrollmentType.setMethodName("getEnrollmentDesc");
        colEnrollmentType.setColumnWidth("20%");
        colEnrollmentType.setIsHeaderLink(true);
        colEnrollmentType.setHeaderLinkTitle("Sort by: Policy Type");
        colEnrollmentType.setDBColumnName("ENROL_DESCRIPTION");
        addColumn(colEnrollmentType);

        //Setting properties for cust. endorsement no.
        Column colCustEndorsementNo = new Column("Cust. Endorsement No.");
        colCustEndorsementNo.setMethodName("getEndorsementNbr");
        colCustEndorsementNo.setColumnWidth("25%");
        colCustEndorsementNo.setIsHeaderLink(true);
        colCustEndorsementNo.setHeaderLinkTitle("Sort by: Cust. Endorsement No.");
        colCustEndorsementNo.setDBColumnName("CUST_ENDORSEMENT_NUMBER");
        addColumn(colCustEndorsementNo);

        //Setting properties for previous Policy No.
        Column colPreviousPolicyNo = new Column("Previous Policy No.");
        colPreviousPolicyNo.setMethodName("getPrevPolicyNbr");
        colPreviousPolicyNo.setColumnWidth("20%");
        colPreviousPolicyNo.setIsHeaderLink(true);
        colPreviousPolicyNo.setHeaderLinkTitle("Sort by: Previous Policy No.");
        colPreviousPolicyNo.setDBColumnName("RENEWAL_POLICY_NUMBER");
        addColumn(colPreviousPolicyNo);

        //Setting properties for workflow
        Column colWorkflow = new Column("Workflow");
        colWorkflow.setMethodName("getWorkflow");
        colWorkflow.setColumnWidth("15%");
        colWorkflow.setIsHeaderLink(true);
        colWorkflow.setHeaderLinkTitle("Sort by: Workflow");
        colWorkflow.setDBColumnName("EVENT_NAME");
        addColumn(colWorkflow);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }// end of public void setTableProperties()
}// end of BatchPolicyTable class
