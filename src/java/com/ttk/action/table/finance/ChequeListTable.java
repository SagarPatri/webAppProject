/**
 * @ (#) ChequeListTable.java June 12, 2006
 * Project       : TTK HealthCare Services
 * File          : ChequeListTable.java
 * Author        : Harsha Vardhan B N
 * Company       : Span Systems Corporation
 * Date Created  : June 12, 2006
 *
 * @author       :
 * Modified by   : Harsha Vardhan B N
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ChequeListTable extends Table {
    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */

        public void setTableProperties()
        {
            setRowCount(10);
            setCurrentPage(1);
            setPageLinkCount(10);

            //Setting properties for Cheque No.
            Column colChequeNo = new Column("Payment Trans Ref No.");
            colChequeNo.setMethodName("getChequeNo");
            colChequeNo.setColumnWidth("15%");
            colChequeNo.setIsLink(true);
            colChequeNo.setLinkTitle("Edit Cheque No");
            colChequeNo.setIsHeaderLink(true);
            colChequeNo.setHeaderLinkTitle("Sort by Cheque No.");
            colChequeNo.setDBColumnName("CHECK_NUM");
            addColumn(colChequeNo);

            //Setting properties for Float Account No.
            Column colFloatAcctNO = new Column("Float Account No.");
            colFloatAcctNO.setMethodName("getFloatAcctNo");
            colFloatAcctNO.setColumnWidth("12%");
            colFloatAcctNO.setIsHeaderLink(true);
            colFloatAcctNO.setHeaderLinkTitle("Sort by Float Account No.");
            colFloatAcctNO.setDBColumnName("FLOAT_ACCOUNT_NUMBER");
            addColumn(colFloatAcctNO);

            //Setting properties for Status
            Column colStatus = new Column("Status");
            colStatus.setMethodName("getStatusDesc");
            colStatus.setColumnWidth("9%");
            colStatus.setIsHeaderLink(true);
            colStatus.setHeaderLinkTitle("Sort by Status");
            colStatus.setDBColumnName("CHECK_STATUS");
            addColumn(colStatus);


            //Setting properties for Cheque Date
            Column colChequeDate = new Column("Cheque Date");
            colChequeDate.setMethodName("getFormattedChequeDate");
            colChequeDate.setColumnWidth("10%");
            colChequeDate.setIsHeaderLink(true);
            colChequeDate.setHeaderLinkTitle("Sort by Cheque Date");
            colChequeDate.setDBColumnName("CHECK_DATE");
            addColumn(colChequeDate);

            //Setting properties for Claim Settlement No.
            Column colClaimSettleNO = new Column("Claim Settlement No.");
            colClaimSettleNO.setMethodName("getClaimSettNo");
            colClaimSettleNO.setColumnWidth("16%");
            colClaimSettleNO.setIsHeaderLink(true);
            colClaimSettleNO.setHeaderLinkTitle("Sort by Claim Settlement No.");
            colClaimSettleNO.setDBColumnName("CLAIM_SETTLEMENT_NO");
            addColumn(colClaimSettleNO);

            //Setting properties for Claim Type
            Column colClaimType = new Column("Claim Type");
            colClaimType.setMethodName("getClaimTypeDesc");
            colClaimType.setColumnWidth("8%");
            colClaimType.setIsHeaderLink(true);
            colClaimType.setHeaderLinkTitle("Sort by Claim Type");
            colClaimType.setDBColumnName("CLAIM_TYPE");
            addColumn(colClaimType);

            //Setting properties for Insurance Company
            Column colInsComp = new Column("Insurance Company");
            colInsComp.setMethodName("getInsCompName");
            colInsComp.setColumnWidth("18%");
            colInsComp.setIsHeaderLink(true);
            colInsComp.setHeaderLinkTitle("Sort by Insurance Company");
            colInsComp.setDBColumnName("INS_COMP_NAME");
            addColumn(colInsComp);
            
            //Setting properties forVidal Health TPA Branch
            Column colOffice = new Column("Branch Location");
            colOffice.setMethodName("getOfficeName");
            colOffice.setColumnWidth("14%");
            colOffice.setIsHeaderLink(true);
            colOffice.setHeaderLinkTitle("Sort by Vidal Health Branch");
            colOffice.setDBColumnName("OFFICE_NAME");
            addColumn(colOffice);
        }//end of public void setTableProperties()
    }//end of class ChequeListTable