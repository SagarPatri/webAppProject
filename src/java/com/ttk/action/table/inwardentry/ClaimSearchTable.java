/**
 * @ (#) ClaimSearchTable.java July 4th, 2007
 * Project      : TTK HealthCare Services
 * File         : ClaimSearchTable.java
 * Author       : Raghavendra T M
 * Company      : Span Info tech
 * Date Created : July 4th
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.inwardentry;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class ClaimSearchTable extends Table
{

    private static final long serialVersionUID = 1L;

    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Claim No.
        Column colPolicyNo = new Column("Claim No.");
        colPolicyNo.setMethodName("getClaimNbr");
        colPolicyNo.setColumnWidth("30%");
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setIsLink(true);
        colPolicyNo.setLinkTitle("Edit Claim No.");
        colPolicyNo.setHeaderLinkTitle("Sort by: Claim No.");
        colPolicyNo.setDBColumnName("CLAIM_NUMBER");
        addColumn(colPolicyNo);
        
        //Setting properties for Enrollment Id
        Column colEnrollmentId = new Column("Enrollment Id");
        colEnrollmentId.setMethodName("getEnrollmentID");
        colEnrollmentId.setColumnWidth("30%");
        colEnrollmentId.setIsHeaderLink(true);
        colEnrollmentId.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentId);

        //Setting properties for Member Name
        Column colClaimantName = new Column("Member Name");
        colClaimantName.setMethodName("getName");
        colClaimantName.setColumnWidth("20%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("CLAIMANT_NAME");
        addColumn(colClaimantName);

        //Setting properties for Gender
        Column colGender = new Column("Gender");
        colGender.setMethodName("getGenderDescription");
        colGender.setColumnWidth("12%");
        colGender.setIsHeaderLink(true);
        colGender.setHeaderLinkTitle("Sort by: Gender");
        colGender.setDBColumnName("GENDER");
        addColumn(colGender);

        //Setting properties for Age (Yrs)
        Column colAge = new Column("Age (Yrs)");
        colAge.setMethodName("getAge");
        colAge.setColumnWidth("8%");
        colAge.setIsHeaderLink(true);
        colAge.setHeaderLinkTitle("Sort by: Age (Yrs)");
        colAge.setDBColumnName("MEM_AGE");
        addColumn(colAge);

    }// end of public void setTableProperties()
}// end of ClaimSearchTable class