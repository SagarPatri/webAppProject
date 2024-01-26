
/*package com.ttk.action.table.finance;

public class HospReviewTable {

}
*/

package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;
/**
 * This class is added for cr koc 1103
 * added eft
 */
public class HospReviewTable extends Table {

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */

        public void setTableProperties()
        {
            setRowCount(10);
            setCurrentPage(1);
            setPageLinkCount(10);

            
            //Setting properties for Hospital Name
            Column colAccountName = new Column("Hospital Name");
            colAccountName.setMethodName("getHospitalName");
            colAccountName.setColumnWidth("20%");
            colAccountName.setLinkTitle("Edit Hospital Name");
            colAccountName.setIsLink(true);
            colAccountName.setIsHeaderLink(true);
            colAccountName.setHeaderLinkTitle("Sort by Hospital Name");
            colAccountName.setDBColumnName("HOSP_NAME");
           // colAccountName.setLinkParamName("SecondLink");
            addColumn(colAccountName);
            
            
          //Setting properties for DHA ID
            Column colDhaId = new Column("Civil ID");
            colDhaId.setMethodName("getDhaId");
            colDhaId.setColumnWidth(" 10%");
            colDhaId.setIsHeaderLink(true);
            colDhaId.setHeaderLinkTitle("Sort by DHA ID");
            colDhaId.setDBColumnName("HOSP_LICENC_NUMB");
            addColumn(colDhaId);
            
          //Setting properties for Empanal Status
            Column colEmnalDesc = new Column("Empanal Status");
            colEmnalDesc.setMethodName("getHospitalEmpnalDesc");
            colEmnalDesc.setColumnWidth(" 10%");
            colEmnalDesc.setIsHeaderLink(true);
            colEmnalDesc.setHeaderLinkTitle("Sort by Empanal Status");
            colEmnalDesc.setDBColumnName("EMPANEL_DESCRIPTION");
            addColumn(colEmnalDesc);
         
            //Setting properties for Hospital Account No
            Column colHospBankACNo = new Column("Hospital Account No");
            colHospBankACNo.setMethodName("getBankAccno");
            colHospBankACNo.setColumnWidth(" 15%");
            colHospBankACNo.setIsHeaderLink(true);
            colHospBankACNo.setHeaderLinkTitle("Sort by Hospital Account No");
            colHospBankACNo.setDBColumnName("ACCOUNT_NUMBER");
            addColumn(colHospBankACNo);
            
          //Setting properties for Bank Name
            Column colBankName = new Column("Bank Name");
            colBankName.setMethodName("getBankname");
            colBankName.setColumnWidth(" 10%");
            colBankName.setIsHeaderLink(true);
            colBankName.setHeaderLinkTitle("Sort by Bank Name");
            colBankName.setDBColumnName("Bank_Name");
            addColumn(colBankName);
            
          //Setting properties for IBAN
            Column colIban = new Column("IBAN");
            colIban.setMethodName("getiBanNo");
            colIban.setColumnWidth(" 10%");
            colIban.setIsHeaderLink(true);
            colIban.setHeaderLinkTitle("Sort by IBAN");
            colIban.setDBColumnName("IBAN_NO");
            addColumn(colIban);
            
          //Setting properties for SWIFT CODE
            Column colSwiftCode = new Column("Swift Code");
            colSwiftCode.setMethodName("getSwiftCode");
            colSwiftCode.setColumnWidth(" 10%");
            colSwiftCode.setIsHeaderLink(true);
            colSwiftCode.setHeaderLinkTitle("Sort by Swift Code");
            colSwiftCode.setDBColumnName("Swift_Code");
            addColumn(colSwiftCode);
            
            
            //Setting properties for TTK Branch
            Column colTTKBranch = new Column("Branch Location");
            colTTKBranch.setMethodName("getOfficename");
            colTTKBranch.setColumnWidth(" 15%");
            colTTKBranch.setIsHeaderLink(true);
            colTTKBranch.setHeaderLinkTitle("Sort by Vidal Health Branch");
            colTTKBranch.setDBColumnName("OFFICE_NAME");
            addColumn(colTTKBranch);
          //Setting properties for check box
           /* Column colSelect = new Column("Select");
            colSelect.setComponentType("checkbox");
            colSelect.setComponentName("chkopt");
            addColumn(colSelect);
            */
        }//end of public void setTableProperties()

	}


