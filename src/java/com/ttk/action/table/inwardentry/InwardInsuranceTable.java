/**
* @ (#) InsuranceCompanyTable.java Feb 2, 2006
* Project 		: TTK HealthCare Services
* File 			: InsuranceCompanyTable.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created 	: Feb 2, 2006
*
* @author 		: Pradeep R
* Modified by 	:
* Modified date :
* Reason 		:
*/

package com.ttk.action.table.inwardentry;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;



public class InwardInsuranceTable extends Table
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

        //Setting properties for Office Code
        Column colOffice = new Column("Office Code");
        colOffice.setMethodName("getCompanyCodeNbr");
        colOffice.setColumnWidth("20%");
        colOffice.setIsLink(true);
        colOffice.setIsHeaderLink(true);
        colOffice.setHeaderLinkTitle("Sort by: Office Code");
        colOffice.setDBColumnName("INS_COMP_CODE_NUMBER");
        addColumn(colOffice);

        //Setting properties for Insurance Company
        Column colInsCompany = new Column("Healthcare Company");
        colInsCompany.setMethodName("getCompanyName");
        colInsCompany.setColumnWidth("55%");
        colInsCompany.setIsHeaderLink(true);
        colInsCompany.setHeaderLinkTitle("Sort by: Healthcare Company");
        colInsCompany.setDBColumnName("INS_COMP_NAME");
        addColumn(colInsCompany);

        //Setting properties for user Vidal Health TPA Branch
        Column colUserType = new Column("Vidal Health Branch");
        colUserType.setMethodName("getTTKBranch");
        colUserType.setColumnWidth("25%");
        colUserType.setIsHeaderLink(true);
        colUserType.setHeaderLinkTitle("Sort by: Vidal Health Branch");
        colUserType.setDBColumnName("OFFICE_NAME");
        addColumn(colUserType);

    }// end of public void setTableProperties()
}// end of InwardInsuranceTable class

