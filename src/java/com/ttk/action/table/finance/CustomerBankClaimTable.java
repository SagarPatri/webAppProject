package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;
import com.ttk.common.TTKCommon;
/**
 * This class is added for cr koc 1103
 * added eft
 */
public class CustomerBankClaimTable extends Table {

	 /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */

        public void setTableProperties()
        {
            setRowCount(10);
            setCurrentPage(1);
            setPageLinkCount(10);
          //Setting properties for Scheme No
            Column colPolicyNo = new Column("Policy NO.");
            colPolicyNo.setMethodName("getPolicyNumber");
            colPolicyNo.setColumnWidth("20%");
            colPolicyNo.setIsLink(true);
            colPolicyNo.setLinkTitle("Edit PolicyNO");
            colPolicyNo.setIsHeaderLink(true);
            colPolicyNo.setHeaderLinkTitle("Sort by Policy NO.");
            colPolicyNo.setDBColumnName("POLICY_NUMBER");
            addColumn(colPolicyNo);
                      
            
            
            //Setting properties for Claim NO
            Column colAccountNo = new Column("Claim NO.");
            colAccountNo.setMethodName("getClaimNo");
            colAccountNo.setColumnWidth("15%");
            colAccountNo.setIsLink(true);
            colAccountNo.setLinkTitle("Edit Claim NO");
            colAccountNo.setIsHeaderLink(true);
            colAccountNo.setHeaderLinkTitle("Sort by Claim NO");
            colAccountNo.setDBColumnName("CLAIM_NUMBER");
            colAccountNo.setLinkParamName("SecondLink");
            addColumn(colAccountNo);

            //Setting properties for Claim Settelment No
            Column colAccountName = new Column("Claim Settelment No");
            colAccountName.setMethodName("getClmSetmentno");
            colAccountName.setColumnWidth("15%");
            colAccountName.setLinkTitle("Edit Claim Settlement No");
            //colAccountName.setIsLink(true);
            colAccountName.setIsHeaderLink(true);
            colAccountName.setHeaderLinkTitle("Sort by Claim Settlement No");
            colAccountName.setDBColumnName("CLAIM_SETTLEMENT_NUMBER");
           // colAccountName.setLinkParamName("SecondLink");
            addColumn(colAccountName);
          
            //Setting properties for Enrollment NO
            Column colEnrollnmbr = new Column("Enrollment No");
            colEnrollnmbr.setMethodName("getEnrollNmbr");
            colEnrollnmbr.setColumnWidth("10%");
                       
            colEnrollnmbr.setIsHeaderLink(true);
            colEnrollnmbr.setHeaderLinkTitle("Sort by Enrollment No");
            colEnrollnmbr.setDBColumnName("TPA_ENROLLMENT_NUMBER");
            
            addColumn(colEnrollnmbr);
            
            //Setting properties for Claiment Name
            Column colBankName = new Column("Member Name");
            colBankName.setMethodName("getClimentName");
            colBankName.setColumnWidth("20%");
            colBankName.setIsHeaderLink(true);
            colBankName.setHeaderLinkTitle("Sort by Member Name");
            colBankName.setDBColumnName("CLAIMANT_NAME");
            addColumn(colBankName);
          
            //Setting properties for Policy Type
            Column colEnrType = new Column("Scheme Type");
            colEnrType.setMethodName("getPolicyType");
            colEnrType.setColumnWidth("10%");
            colEnrType.setIsHeaderLink(true);
            colEnrType.setHeaderLinkTitle("Sort by Scheme Type");
            colEnrType.setDBColumnName("POLICY_TYPE");
            addColumn(colEnrType);

            //Setting properties for Account No
            Column colBankACNo = new Column("Account No");
            colBankACNo.setMethodName("getBankAccno");
            colBankACNo.setColumnWidth("15%");
            colBankACNo.setIsHeaderLink(true);
            colBankACNo.setHeaderLinkTitle("Sort by Account No");
            colBankACNo.setDBColumnName("BANK_ACCOUNT_NO");
            addColumn(colBankACNo);
            
            //Setting properties for Check Issued To
            Column colTTKBranch = new Column("Cheque Issued To");
            colTTKBranch.setMethodName("getCheckIssuedTo");
            colTTKBranch.setColumnWidth("10%");
            colTTKBranch.setIsHeaderLink(true);
            colTTKBranch.setHeaderLinkTitle("Sort by Check Issued To");
            colTTKBranch.setDBColumnName("TPA_CHEQUE_ISSUED_GENERAL_TYPE");
            addColumn(colTTKBranch);
          //Setting properties for check box
            Column colSelect = new Column("Select");
            colSelect.setComponentType("checkbox");
            colSelect.setComponentName("chkopt");
            addColumn(colSelect);
            
        }//end of public void setTableProperties()


}
