/**
 * @ (#) InwardClaimsSearchTable.java July 18th, 2006
 * Project      : TTK HealthCare Services
 * File         : ClaimsSearchTable.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : July 18th
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class InwardClaimsSearchTable extends Table
{

	public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Inward No
        Column colInwardNo = new Column("Inward No.");
        colInwardNo.setMethodName("getInwardNbr");
        colInwardNo.setColumnWidth("15%");
        colInwardNo.setIsHeaderLink(true);
        colInwardNo.setHeaderLinkTitle("Sort by: Inward No");
        colInwardNo.setIsLink(true);
        colInwardNo.setLinkTitle("Edit Inward No");
        colInwardNo.setDBColumnName("CLAIMS_INWARD_NO");
        addColumn(colInwardNo);

        //Setting properties for Received Date
        Column colReceivedDate = new Column("Received Date");
        colReceivedDate.setMethodName("getInwardReceivedDate");
        colReceivedDate.setColumnWidth("15%");
        colReceivedDate.setIsHeaderLink(true);
        colReceivedDate.setHeaderLinkTitle("Sort by: Received Date");
        colReceivedDate.setDBColumnName("RCVD_DATE");
        addColumn(colReceivedDate);

        //Setting properties for Enrollment Id
        Column colEnrollmentId = new Column("Enrollment Id");
        colEnrollmentId.setMethodName("getEnrollmentID");
        colEnrollmentId.setColumnWidth("20%");
        colEnrollmentId.setIsHeaderLink(true);
        colEnrollmentId.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentId);

        //Setting properties for Member Name
        Column colClaimantName= new Column("Member Name");
        colClaimantName.setMethodName("getClaimantName");
        colClaimantName.setColumnWidth("20%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("CLAIMANT_NAME");
        addColumn(colClaimantName);

        //Setting properties for Corporate Name
        Column colCorporateName = new Column("Corporate Name");
        colCorporateName.setMethodName("getGroupName");
        colCorporateName.setColumnWidth("20%");
        colCorporateName.setIsHeaderLink(true);
        colCorporateName.setHeaderLinkTitle("Sort by: Corporate Name");
        colCorporateName.setDBColumnName("CORPORATE_NAME");
        addColumn(colCorporateName);

        //Setting properties for Claim Type
        Column colClaimType = new Column("Claim Type");
        colClaimType.setMethodName("getClaimTypeDesc");
        colClaimType.setColumnWidth("10%");
        colClaimType.setIsHeaderLink(true);
        colClaimType.setHeaderLinkTitle("Sort by: Claim Type");
        colClaimType.setDBColumnName("DESCRIPTION");
        addColumn(colClaimType);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }// end of public void setTableProperties()
}
