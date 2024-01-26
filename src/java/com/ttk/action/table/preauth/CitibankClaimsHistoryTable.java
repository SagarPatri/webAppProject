/**
 * @ (#) CitibankClaimsHistoryTable.java 8th Sep 2008
 * Project      	: TTK HealthCare Services
 * File         	: CitibankClaimsHistoryTable.java
 * Author       	: Sendhil Kumar V
 * Company      	: Span Systems Corporation
 * Date Created 	: 8th Sep 2008
 *
 * @author       	: Sendhil Kumar V
 * Modified by   	:
 * Modified date 	:
 * Reason        	:
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Claims details
 *
 */
public class CitibankClaimsHistoryTable extends Table
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

        //Setting properties for Claim No
        Column colPolicyNo = new Column("Claim No.");
        colPolicyNo.setMethodName("getClaimNbr");
        colPolicyNo.setColumnWidth("40%");
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setHeaderLinkTitle("Sort by: Claim No.");
        colPolicyNo.setIsLink(true);
        colPolicyNo.setLinkTitle("View History");
        colPolicyNo.setDBColumnName("CLAIMNO");
        addColumn(colPolicyNo);
        
        //Setting properties for Claim No
        Column colClaimYear = new Column("Claim Year");
        colClaimYear.setMethodName("getClaimYear");
        colClaimYear.setColumnWidth("40%");
        colClaimYear.setIsHeaderLink(true);
        colClaimYear.setHeaderLinkTitle("Sort by: Claim Year");
        colClaimYear.setLinkTitle("View History");
        colClaimYear.setDBColumnName("CLAIMYEAR");
        addColumn(colClaimYear);

    }//end of setTableProperties()
}//end of CitibankClaimsHistoryTable
