/*package com.ttk.action.table.finance;

public class CustomerBankHospitalTable {

}
*/

package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;
/**
 * This class is added for cr koc 1103
 * added eft
 */
public class CustomerBankHospitalTable extends Table {

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */

        public void setTableProperties()
        {
            setRowCount(10);
            setCurrentPage(1);
            setPageLinkCount(10);
            //Setting properties for Empanelment No
            Column colAccountNo = new Column("Empanelment No.");
            colAccountNo.setMethodName("getHospitalEmnalNumber");
            colAccountNo.setColumnWidth("20%");
            colAccountNo.setIsLink(true);
            colAccountNo.setLinkTitle("Edit Empanelment No");
            colAccountNo.setIsHeaderLink(true);
            colAccountNo.setHeaderLinkTitle("Sort by Empanelment No.");
            colAccountNo.setDBColumnName("EMPANEL_NUMBER");
            addColumn(colAccountNo);
           
            
            //Setting properties for Hospital Name
            Column colAccountName = new Column("Provider Name");
            colAccountName.setMethodName("getHospitalName");
            colAccountName.setColumnWidth("20%");
            colAccountName.setLinkTitle("Edit Provider Name");
            //colAccountName.setIsLink(true);
            colAccountName.setIsHeaderLink(true);
            colAccountName.setHeaderLinkTitle("Sort by Provider Name");
            colAccountName.setDBColumnName("HOSP_NAME");
           // colAccountName.setLinkParamName("SecondLink");
            addColumn(colAccountName);
            
          //Setting properties for Empanal Status
            Column colEmnalDesc = new Column("Empanal Status");
            colEmnalDesc.setMethodName("getHospitalEmpnalDesc");
            colEmnalDesc.setColumnWidth(" 15%");
            colEmnalDesc.setIsHeaderLink(true);
            colEmnalDesc.setHeaderLinkTitle("Sort by Empanal Status");
            colEmnalDesc.setDBColumnName("EMPANEL_DESCRIPTION");
            addColumn(colEmnalDesc);
         
            //Setting properties for Hospital Account No
            Column colHospBankACNo = new Column("Provider Account No");
            colHospBankACNo.setMethodName("getBankAccno");
            colHospBankACNo.setColumnWidth(" 15%");
            colHospBankACNo.setIsHeaderLink(true);
            colHospBankACNo.setHeaderLinkTitle("Sort by Provider Account No");
            colHospBankACNo.setDBColumnName("ACCOUNT_NUMBER");
            addColumn(colHospBankACNo);
            
            //Setting properties for TTK Branch
            Column colTTKBranch = new Column("Vidal Health Branch");
            colTTKBranch.setMethodName("getOfficename");
            colTTKBranch.setColumnWidth(" 15%");
            colTTKBranch.setIsHeaderLink(true);
            colTTKBranch.setHeaderLinkTitle("Sort by Vidal Health Branch");
            colTTKBranch.setDBColumnName("OFFICE_NAME");
            addColumn(colTTKBranch);
          //Setting properties for check box
            Column colSelect = new Column("Select");
            colSelect.setComponentType("checkbox");
            colSelect.setComponentName("chkopt");
            addColumn(colSelect);
            
        }//end of public void setTableProperties()

	}


