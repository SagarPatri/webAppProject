/**
* @ (#) BenefitListTable.java
* Project       : TTK HealthCare Services
* File          : ClaimsTable.java
* Author        : Balaji C R B
* Company       : Span Infotech India Pvt. Ltd
* Date Created  : July 2,2008

* @author       : Balaji C R B
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class BenefitListTable extends Table
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

        //Setting properties for Member Name
        Column colClaimantName=new Column("Member Name");
        colClaimantName.setMethodName("getClaimantName");
        colClaimantName.setColumnWidth("20%");
        colClaimantName.setIsHeaderLink(true);
        colClaimantName.setImageName("");
        colClaimantName.setImageTitle("");
        colClaimantName.setHeaderLinkTitle("Sort by: Member Name");
        colClaimantName.setDBColumnName("CLAIMANT_NAME");
        addColumn(colClaimantName);

        //Setting properties for Claim No.
        Column colClaimNo=new Column("Claim No.");
        colClaimNo.setMethodName("getClaimNbr");
        colClaimNo.setColumnWidth("20%");
        colClaimNo.setIsHeaderLink(true);
        colClaimNo.setHeaderLinkTitle("Sort by: Claim No.");
        colClaimNo.setDBColumnName("CLAIM_NUMBER");
        addColumn(colClaimNo);

        //Setting properties for  Type of Benefit
        Column colTypeOfBenefit=new Column("Type of Benefit");
        colTypeOfBenefit.setMethodName("getBenefitType");
        colTypeOfBenefit.setColumnWidth("20%");
        colTypeOfBenefit.setIsHeaderLink(true);
        colTypeOfBenefit.setHeaderLinkTitle("Sort by: Type of Benefit");
        colTypeOfBenefit.setDBColumnName("BENEFIT_TYPE");
        addColumn(colTypeOfBenefit);

        //Setting properties for  Parent Claim No.
        Column colParentClaimNo=new Column("Parent Claim No.");
        colParentClaimNo.setMethodName("getParentClaimNbr");
        colParentClaimNo.setColumnWidth("20%");
        colParentClaimNo.setIsHeaderLink(true);
        colParentClaimNo.setHeaderLinkTitle("Sort by: Parent Claim No.");
        colParentClaimNo.setDBColumnName("CLAIMANT_NAME");
        addColumn(colParentClaimNo);

        //Setting properties for  Claim Approved Date
        Column colCBClaimApprovedDt=new Column("Parent Claim Approved Date");
        colCBClaimApprovedDt.setMethodName("getCBApprovedDate");
        colCBClaimApprovedDt.setColumnWidth("15%");
        colCBClaimApprovedDt.setIsHeaderLink(true);
        colCBClaimApprovedDt.setHeaderLinkTitle("Sort by: Claim Approved Date");
        colCBClaimApprovedDt.setDBColumnName("CB_APPROVED_DATE");
        addColumn(colCBClaimApprovedDt);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("radio");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    } //end of setTableProperties()
}// end of BenefitListTable