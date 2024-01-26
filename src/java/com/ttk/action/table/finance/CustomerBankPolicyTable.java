package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;
/**
 * This class is added for cr koc 1103
 * added eft
 */
public class CustomerBankPolicyTable extends Table {

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
            Column colAccountNo = new Column("Policy No.");
            colAccountNo.setMethodName("getPolicyNumber");
            colAccountNo.setColumnWidth("20%");
            colAccountNo.setIsLink(true);
            colAccountNo.setLinkTitle("Edit PolicyNO");
            colAccountNo.setIsHeaderLink(true);
            colAccountNo.setHeaderLinkTitle("Sort by PolicyNO.");
            colAccountNo.setDBColumnName("POLICY_NUMBER");
            addColumn(colAccountNo);
            
            
            //Setting properties for Policy Type
            Column colPolicyCategory = new Column("Policy Category");
            colPolicyCategory.setMethodName("getPolicyCategory");
            colPolicyCategory.setColumnWidth("10%");
            colPolicyCategory.setIsHeaderLink(true);
            colPolicyCategory.setHeaderLinkTitle("Sort by Policy Category");
            colPolicyCategory.setDBColumnName("CLASSIFICATION");
            addColumn(colPolicyCategory);

            //Setting properties for Enrollment NO
            Column colAccountName = new Column("Enrollment No");
            colAccountName.setMethodName("getEnrollNmbr");
            colAccountName.setColumnWidth("15%");
            colAccountName.setLinkTitle("Edit Enrollment No");
            colAccountName.setIsLink(true);
            colAccountName.setIsHeaderLink(true);
            colAccountName.setHeaderLinkTitle("Sort by Enrollment No");
            colAccountName.setDBColumnName("TPA_ENROLLMENT_NUMBER");
            colAccountName.setLinkParamName("SecondLink");
            addColumn(colAccountName);
         
            //Setting properties for Policy Type
            Column colEnrType = new Column("Scheme Type");
            colEnrType.setMethodName("getPolicyType");
            colEnrType.setColumnWidth("10%");
            colEnrType.setIsHeaderLink(true);
            colEnrType.setHeaderLinkTitle("Sort by Scheme Type");
            colEnrType.setDBColumnName("POLICY_TYPE");
            addColumn(colEnrType);

          //Setting properties for Beneficiary Name
            Column colBankName = new Column("Beneficiary Name");
            colBankName.setMethodName("getInsureName");
            colBankName.setColumnWidth("20%");
            colBankName.setIsHeaderLink(true);
            colBankName.setHeaderLinkTitle("Sort by Beneficiary Name");
            colBankName.setDBColumnName("BENEFICIARY_NAME");
            addColumn(colBankName);
          
            //Setting properties for Account No
            Column colBankACNo = new Column("Account No");
            colBankACNo.setMethodName("getBankAccno");
            colBankACNo.setColumnWidth("15%");
            colBankACNo.setIsHeaderLink(true);
            colBankACNo.setHeaderLinkTitle("Sort by  Account No");
            colBankACNo.setDBColumnName("BANK_ACCOUNT_NO");
            addColumn(colBankACNo);
           
          //Setting properties for Check Issued To
            Column colTTKBranch = new Column("Cheque Issued To");
            colTTKBranch.setMethodName("getCheckIssuedTo");
            colTTKBranch.setColumnWidth("10%");
            colTTKBranch.setIsHeaderLink(true);
            colTTKBranch.setHeaderLinkTitle("Sort by Vidal Health Branch");
            colTTKBranch.setDBColumnName("TPA_CHEQUE_ISSUED_GENERAL_TYPE");
            addColumn(colTTKBranch);

          //Setting properties for check box
            Column colSelect = new Column("Select");
            colSelect.setComponentType("checkbox");
            colSelect.setComponentName("chkopt");
            addColumn(colSelect);
            
        }//end of public void setTableProperties()

	}


