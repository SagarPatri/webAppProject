/**
 * @ (#) InvoiceTable.java Nov 4th, 2006
 * Project      : TTK HealthCare Services
 * File         :InvoiceTable.java
 * Author       : Arun K M
 * Company      : Span Systems Corporation
 * Date Created : Nov 4th, 2006
 *
 * @author       :Arun K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;
/**
 *  This class provides the information of Float details
 *
 */

public class InvoiceTable extends Table{
    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
       //Setting properties for Scheme No.
        Column colPolicyNo = new Column("Policy No.");
        colPolicyNo.setMethodName("getPolicyNbr");
        colPolicyNo.setColumnWidth("20%");
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setHeaderLinkTitle("Sort by: Policy No.");
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);

        //Setting properties for Product/SchemeName
        Column colProductSchemeName  =new Column("Product/Scheme Name");
        colProductSchemeName.setMethodName("getProductName");
        colProductSchemeName.setColumnWidth("20%");
        colProductSchemeName.setIsHeaderLink(true);
        colProductSchemeName.setHeaderLinkTitle("Sort by: ProductSchemeName");
        colProductSchemeName.setDBColumnName("PRODUCT_NAME");
        addColumn(colProductSchemeName);

        //Setting properties for Premium Amt
        Column colPremiumAmt =new Column("Premium Amt. (Rs)");
        colPremiumAmt.setMethodName("getTotalPremium");
        colPremiumAmt.setColumnWidth("20%");
        colPremiumAmt.setIsHeaderLink(true);
        colPremiumAmt.setHeaderLinkTitle("Sort by: Premium Amt. (Rs)");
        colPremiumAmt.setDBColumnName("NET_PREMIUM");
        addColumn(colPremiumAmt);

        //Setting properties for Commission
        Column colCommission =new Column("Commission(%)");
        colCommission.setMethodName("getCommision");
        colCommission.setColumnWidth("20%");
        colCommission.setIsHeaderLink(true);
        colCommission.setHeaderLinkTitle("Sort by: Commission");
        colCommission.setDBColumnName("COMMISSION");
        addColumn(colCommission);

        //Setting properties for Commission Amt
        Column colCommissionAmt =new Column("Commission Amt. (Rs)");
        colCommissionAmt.setMethodName("getCommissionAmt");
        colCommissionAmt.setColumnWidth("20%");
        colCommissionAmt.setIsHeaderLink(true);
        colCommissionAmt.setHeaderLinkTitle("Sort by: Commission Amt. (Rs)");
        colCommissionAmt.setDBColumnName("COMMISSION_AMT");
        addColumn(colCommissionAmt);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }//end of setTableProperties()

}

