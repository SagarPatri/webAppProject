/**
 * @ (#) InvestigationTable.java 6th May 2006
 * Project      : TTK HealthCare Services
 * File         : InvestigationTable.java
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : 6th May 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Investigation details
 *
 */
public class InvestigationTable extends Table{

    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Investigation No
        Column colInvsNo = new Column("Investigation No.");
        colInvsNo.setMethodName("getInvestigationNo");
        colInvsNo.setColumnWidth("17%");
        colInvsNo.setIsHeaderLink(true);
        colInvsNo.setHeaderLinkTitle("Sort by: Investigation No.");
        colInvsNo.setIsLink(true);
        colInvsNo.setLinkTitle("Edit Investigation");
        colInvsNo.setDBColumnName("INVESTIGATION_ID");
        addColumn(colInvsNo);

        //Setting properties for Marked Date
        Column colMarkedDate = new Column("Marked Date");
        colMarkedDate.setMethodName("getDocumentDateTime");
        colMarkedDate.setColumnWidth("17%");
        colMarkedDate.setIsHeaderLink(true);
        colMarkedDate.setHeaderLinkTitle("Sort by: Marked Date");
        colMarkedDate.setDBColumnName("MARKED_DATE_TIME");
        addColumn(colMarkedDate);

        //Setting properties for Investigation Agency
        Column colInvsAgency =new Column("Investigation Agency");
        colInvsAgency.setMethodName("getInvestAgencyDesc");
        colInvsAgency.setColumnWidth("20%");
        colInvsAgency.setIsHeaderLink(true);
        colInvsAgency.setHeaderLinkTitle("Sort by: Investigation Agency");
        colInvsAgency.setDBColumnName("AGENCY_NAME");
        addColumn(colInvsAgency);

        //Setting properties for Status
        Column colStatus =new Column("Status");
        colStatus.setMethodName("getStatusDesc");
        colStatus.setColumnWidth("11%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("STATUS");
        addColumn(colStatus);

        //Setting properties for Cashless / Claim No
        Column colPreAuthClmNo =new Column("Cashless / Claim No.");
        colPreAuthClmNo.setMethodName("getPreAuthClaimNo");
        colPreAuthClmNo.setColumnWidth("20%");
        colPreAuthClmNo.setIsHeaderLink(true);
        colPreAuthClmNo.setHeaderLinkTitle("Sort by: Cashless / Claim No.");
        colPreAuthClmNo.setDBColumnName("ID");
        addColumn(colPreAuthClmNo);

        //Setting properties forVidal Health TPABranch
        Column colTTKBranch =new Column("Vidal Health Branch");
        colTTKBranch.setMethodName("getOfficeName");
        colTTKBranch.setColumnWidth("15%");
        colTTKBranch.setIsHeaderLink(true);
        colTTKBranch.setHeaderLinkTitle("Sort by: Vidal Health Branch");
        colTTKBranch.setDBColumnName("OFFICE_NAME");
        addColumn(colTTKBranch);

    }//end of setTableProperties()

}//end of InvestigationTable
