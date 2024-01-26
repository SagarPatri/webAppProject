/**
 * @ (#) AddressLableTable.java Dec 2nd, 2009
 * Project      : TTK HealthCare Services
 * File         : AddressLableTable.java
 * Author       : Navin Kumar R
 * Company      : Span Systems Corporation
 * Date Created : Dec 2nd, 2006
 *
 * @author       :  
 * Modified by   : 
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class AddressLableTable extends Table
{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    public void setTableProperties()
    {
        setRowCount(10000);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Claim Settlement No.
        Column colClaimSettNo = new Column("Claim Settlement No.");
        colClaimSettNo.setMethodName("getClaimSettNo");
        colClaimSettNo.setColumnWidth("21%");
        colClaimSettNo.setIsHeaderLink(true);
        colClaimSettNo.setHeaderLinkTitle("Sort by: Claim Settlement No.");
        colClaimSettNo.setIsLink(true);
        colClaimSettNo.setLinkTitle("Edit Claim Settlement No");
        colClaimSettNo.setDBColumnName("CLAIM_SETTLEMENT_NO");
        addColumn(colClaimSettNo);

        //Setting properties for Enrollment Id
        Column colEnrollmentId = new Column("Enrollment Id");
        colEnrollmentId.setMethodName("getEnrollID");
        colEnrollmentId.setColumnWidth("14%");
        colEnrollmentId.setIsHeaderLink(true);
        colEnrollmentId.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentId);
        
        
        Column colDhpoMemberId = new Column("Member Id");
        colDhpoMemberId.setMethodName("getDhpoMemberId");
        colDhpoMemberId.setColumnWidth("14%");
        colDhpoMemberId.setIsHeaderLink(true);
        colDhpoMemberId.setHeaderLinkTitle("Sort by: DHPO Member Id");
        colDhpoMemberId.setDBColumnName("DHPO_MEMBER_ID");
        addColumn(colDhpoMemberId);
        

        //Setting properties for Member Name
        Column colClaimantName = new Column("Member Name");
        colClaimantName.setMethodName("getClaimantName");
        colClaimantName.setColumnWidth("15%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("MEM_NAME");
        addColumn(colClaimantName);

        //Setting properties for Claim Type
        Column colClaimType = new Column("Claim Type");
        colClaimType.setMethodName("getClaimTypeDesc");
        colClaimType.setColumnWidth("10%");
        colClaimType.setIsHeaderLink(true);
        colClaimType.setHeaderLinkTitle("Sort by: Claim Type");
        colClaimType.setDBColumnName("CLAIM_TYPE");
        addColumn(colClaimType);

        //Setting properties for Approved Date
        Column colApprovedDate = new Column("Approved Date");
        colApprovedDate.setMethodName("getApprovedDate");
        colApprovedDate.setColumnWidth("13%");
        colApprovedDate.setIsHeaderLink(true);
        colApprovedDate.setHeaderLinkTitle("Sort by: Approved Date");
        colApprovedDate.setDBColumnName("CLAIM_APRV_DATE");
        addColumn(colApprovedDate);

       //Setting properties for Payee
        Column colPayee = new Column("Payee");
        colPayee.setMethodName("getInFavorOf");
        colPayee.setColumnWidth("14%");
        colPayee.setIsHeaderLink(true);
        colPayee.setHeaderLinkTitle("Sort by: Payee");
        colPayee.setDBColumnName("IN_FAVOUR_OF");
        addColumn(colPayee);

        //Setting properties for Claim Amt. (Rs)
        Column colChequeNo = new Column("Cheque No.");
        colChequeNo.setMethodName("getChequeNo");
        colChequeNo.setColumnWidth("13%");
        colChequeNo.setIsHeaderLink(true);
        colChequeNo.setHeaderLinkTitle("Sort by: Cheque No.");
        colChequeNo.setDBColumnName("CHECK_NUM");
        addColumn(colChequeNo);
                
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
    }
}
