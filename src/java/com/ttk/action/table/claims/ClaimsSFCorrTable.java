/**

* @ (#) ClaimsSFCorrTable.java Jan 02, 2013
* Project       : TTK HealthCare Services
* File          : ClaimsSFCorrTable.java
* Author        : Manohar
* Company       : RCS
* Date Created  : Jan 02, 2013

* @author       : Manohar
* Modified by   :
* Modified date :
* Reason :
*/
//KOC 1179
package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ClaimsSFCorrTable extends Table
{
	/**
     * This creates the column properties objects for each and
     * every column and adds the column object to the table
     */
	public void setTableProperties()
    {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Claim No.
        Column colClaimNo=new Column("Claim No.");
        colClaimNo.setMethodName("getClaimNumber");
        colClaimNo.setColumnWidth("20%");
        colClaimNo.setIsHeaderLink(true);
        colClaimNo.setHeaderLinkTitle("Sort by: Claim No.");
        colClaimNo.setDBColumnName("claim_number");
        addColumn(colClaimNo);

        //Setting properties for Shortfall No.
        Column colShortfallNo=new Column("Shortfall No.");
        colShortfallNo.setMethodName("getShortfallNumber");
        colShortfallNo.setColumnWidth("25%");
        //colShortfallNo.setIsLink(true);
        colShortfallNo.setLinkTitle("Edit Shortfall No.");
       // colShortfallNo.setIsHeaderLink(true);
        colShortfallNo.setHeaderLinkTitle("Sort by: Shortfall No.");
        colShortfallNo.setDBColumnName("shortfall_id");
        addColumn(colShortfallNo);

        //Setting properties for Shortfall Status.
        Column colShortfallStatus=new Column("Shortfall Status");
        colShortfallStatus.setMethodName("getShortfallStatus");
        colShortfallStatus.setColumnWidth("15%");
        colShortfallStatus.setDBColumnName("shortfall_email_status");
        addColumn(colShortfallStatus);

        //Setting properties for Email ID.
        Column colEmailId=new Column("Email ID");
        colEmailId.setMethodName("getEmailIDStatus");
        colEmailId.setColumnWidth("20%");
        colEmailId.setIsHeaderLink(true);
        colEmailId.setHeaderLinkTitle("Sort by: Email ID");
        colEmailId.setDBColumnName("to_email_id");
        addColumn(colEmailId);

        
        //Setting properties for Mobile No.
        Column colMobileno=new Column("Mobile No.");
        colMobileno.setMethodName("getMobileNo");
        colMobileno.setColumnWidth("13%");
        colMobileno.setDBColumnName("MOBILE_NO");
        addColumn(colMobileno);

        //Setting properties for TTK Branch.
        Column colTTKBranch=new Column("Vidal Health Branch");
        colTTKBranch.setMethodName("getBranch");
        colTTKBranch.setColumnWidth("7%");
        colTTKBranch.setDBColumnName("TTK_BRANCH");
        addColumn(colTTKBranch);
      
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    } //end of setTableProperties()
}// end of ClaimsSFCorrTable class


