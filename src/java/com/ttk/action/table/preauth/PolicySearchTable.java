/**
 * @ (#) PolicySearchTable.java May 6th, 2006
 * Project      : TTK HealthCare Services
 * File         : PolicySearchTable.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : May 6th
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class PolicySearchTable extends Table{


    private static final long serialVersionUID = 1L;
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Scheme No
        Column colPolicyNo = new Column("Scheme No.");
        colPolicyNo.setMethodName("getPolicyNbr");
        colPolicyNo.setColumnWidth("30%");
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setHeaderLinkTitle("Sort by: Scheme No.");
        colPolicyNo.setIsLink(true);
        colPolicyNo.setLinkTitle("Edit Scheme No");
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);

        //Setting properties for Insurance Company
        Column colInsuranceCompany = new Column("Healthcare Company");
        colInsuranceCompany.setMethodName("getCompanyName");
        colInsuranceCompany.setColumnWidth("35%");
        colInsuranceCompany.setIsHeaderLink(true);
        colInsuranceCompany.setHeaderLinkTitle("Sort by: Healthcare Company");
        colInsuranceCompany.setDBColumnName("INS_COMP_NAME");
        addColumn(colInsuranceCompany);

        //Setting properties for Corp. Name
        Column colCorpName = new Column("Corp. Name");
        colCorpName.setMethodName("getGroupName");
        colCorpName.setColumnWidth("35%");
        colCorpName.setIsHeaderLink(true);
        colCorpName.setHeaderLinkTitle("Sort by: Corp. Name");
        colCorpName.setDBColumnName("GROUP_NAME");
        addColumn(colCorpName);
   }// end of public void setTableProperties()
}//end of PolicySearchTable class
