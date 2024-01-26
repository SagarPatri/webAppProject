/**
 * @ (#) BillsTable.java july 21, 2006
 * Project      : TTK HealthCare Services
 * File         : BillsTable.java
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : july 21, 2006
 *
 * @author       :  Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class BillsTable extends Table
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


//      Setting properties for Bill No.
        Column colBillNo = new Column("Bill No.");
        colBillNo.setMethodName("getBillNbr");
        colBillNo.setColumnWidth("20%");
        colBillNo.setIsHeaderLink(true);
        colBillNo.setHeaderLinkTitle("Sort by: Bill No. ");
        colBillNo.setIsLink(true);
        colBillNo.setLinkTitle("Account No.");
        colBillNo.setDBColumnName("BILL_NO");
        addColumn(colBillNo);

        //Setting properties for Bill Date.
        Column colBillDate=new Column("Bill Date");
        colBillDate.setMethodName("getClaimBillDate");
        colBillDate.setColumnWidth("12%");
        colBillDate.setIsHeaderLink(true);
        colBillDate.setHeaderLinkTitle("Sort by: Bill Date");
        colBillDate.setDBColumnName("BILL_DATE");
        addColumn(colBillDate);

        //Setting properties for  Bill Type.
        Column colBillType=new Column("Bill Type");
        colBillType.setMethodName("getBillTypeDesc");
        colBillType.setColumnWidth("21%");
        colBillType.setIsHeaderLink(true);
        colBillType.setHeaderLinkTitle("Sort by: Bill Type");
        colBillType.setDBColumnName("BILL_TYPE");
        addColumn(colBillType);

        //Setting properties for  Bill Amt..
        Column colBillAmt=new Column("Requested Bill Amount");
        colBillAmt.setMethodName("getBillAmount");
        colBillAmt.setColumnWidth("15%");
        colBillAmt.setIsHeaderLink(true);
        colBillAmt.setHeaderLinkTitle("Sort by: Requested Bill Amt. (Rs)");
        colBillAmt.setDBColumnName("BILL_AMOUNT");
        addColumn(colBillAmt);

//      Setting properties for  Approved Bill Amount.
        Column colApprovedBillAmount=new Column("Allowed Amt. (Rs)");
        colApprovedBillAmount.setMethodName("getApprovedBillAmt");
        colApprovedBillAmount.setColumnWidth("15%");
        colApprovedBillAmount.setIsHeaderLink(true);
        colApprovedBillAmount.setHeaderLinkTitle("Sort by: Approved Bill Amount");
        colApprovedBillAmount.setDBColumnName("ALLOWED_AMOUNT");
        addColumn(colApprovedBillAmount);
        
        //Setting properties for Bill Included.
        Column colBillInCludedYN=new Column("Bill Included");
        colBillInCludedYN.setMethodName("getBillIncludedYN");
        colBillInCludedYN.setColumnWidth("15%");
        colBillInCludedYN.setIsHeaderLink(true);
        colBillInCludedYN.setHeaderLinkTitle("Sort by: Bill Included");
        colBillInCludedYN.setDBColumnName("BILL_INCLUDED_YN");
        addColumn(colBillInCludedYN);

        //Setting properties for image
        Column colImage = new Column("");
        colImage.setIsImage(true);
        colImage.setIsImageLink(true);
        colImage.setImageName("getImageName");
        colImage.setImageTitle("getImageTitle");
        addColumn(colImage);


        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }// end of public void setTableProperties()

}// end of bill class




