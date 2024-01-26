/**
 * @ (#) FloatListTable.java June 13, 2006
 * Project      : TTK HealthCare Services
 * File         :FloatListTable.java
 * Author       : Arun K M
 * Company      : Span Systems Corporation
 * Date Created : June 13, 2006
 *
 * @author       :  Arun K M
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

public class FloatListTable extends Table{
    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Float No.
        Column colFloatNo = new Column("Float No.");
        colFloatNo.setMethodName("getFloatNo");
        colFloatNo.setColumnWidth("20%");
        colFloatNo.setIsHeaderLink(true);
        colFloatNo.setHeaderLinkTitle("Sort by: Account No. ");
        colFloatNo.setIsLink(true);
        colFloatNo.setLinkTitle("Account No.");
        colFloatNo.setDBColumnName("FLOAT_ACCOUNT_NUMBER");
        addColumn(colFloatNo);

        //Setting properties for Insurance company name
        Column colFloatName = new Column("Float Name ");
        colFloatName.setMethodName("getFloatAcctName");
        colFloatName.setColumnWidth("25%");
        colFloatName.setIsHeaderLink(true);
        colFloatName.setHeaderLinkTitle("Sort by: Account Type");
        colFloatName.setDBColumnName("FLOAT_ACCOUNT_NAME");
        addColumn(colFloatName);


        //Setting properties for Insurance Company
        Column colInsuranceCompany  =new Column("Insurance Company ");
        colInsuranceCompany.setMethodName("getInsComp");
        colInsuranceCompany.setColumnWidth("23%");
        colInsuranceCompany.setIsHeaderLink(true);
        colInsuranceCompany.setHeaderLinkTitle("Sort by: Insurance Company");
        colInsuranceCompany.setDBColumnName("INS_COMP_NAME");
        addColumn(colInsuranceCompany);

        //Setting properties for Company Code
        Column colCompanyCode =new Column("Insurance Code");
        colCompanyCode.setMethodName("getInsCompCode");
        colCompanyCode.setColumnWidth("17%");
        colCompanyCode.setIsHeaderLink(true);
        colCompanyCode.setHeaderLinkTitle("Sort by: Insurance Code");
        colCompanyCode.setDBColumnName("INS_COMP_CODE_NUMBER");
        addColumn(colCompanyCode);
        
        //Start Ak
        Column colProductAuthority =new Column("Insurance Authority(Product Authority)");
        colProductAuthority.setMethodName("getStrProductAuthority");
        colProductAuthority.setColumnWidth("21%");
        colProductAuthority.setIsHeaderLink(true);
        colProductAuthority.setHeaderLinkTitle("Sort by: ProductAuthority");
        colProductAuthority.setDBColumnName("PAYER_AUTHORITY");
        addColumn(colProductAuthority);

        
        //End AK


//      Setting properties for Status
        Column colStatus =new Column("Status");
        colStatus.setMethodName("getStatusDesc");
        colStatus.setColumnWidth("15%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("STATUS");
        addColumn(colStatus);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }//end of setTableProperties()



}
