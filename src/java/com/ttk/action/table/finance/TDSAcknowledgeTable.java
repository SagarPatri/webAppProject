/**

* @ (#) TDSAcknowledgeTable.java Aug 11, 2009
* Project       : TTK HealthCare Services
* File          : TDSAcknowledgeTable.java
* Author        : Balakrishna Erram
* Company       : Span Systems Corporation
* Date Created  : Aug 11, 2009

* @author       : Balakrishna Erram
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class TDSAcknowledgeTable extends Table
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

        //Setting properties for Insurance Company.
        Column colInsComp=new Column("Insurance Company");
        colInsComp.setMethodName("getInsCompanyName");
        colInsComp.setColumnWidth("25%");
        colInsComp.setIsHeaderLink(true);
        colInsComp.setImageName("");
        colInsComp.setImageTitle("");
        colInsComp.setHeaderLinkTitle("Sort by: Insurance Company");
        colInsComp.setDBColumnName("INS_COMP_NAME");
        addColumn(colInsComp);

        //Setting properties for Financial Year.
        Column colFinYear=new Column("Financial Year");
        colFinYear.setMethodName("getFinancialYear");
        colFinYear.setColumnWidth("20%");
        colFinYear.setIsHeaderLink(true);
        colFinYear.setHeaderLinkTitle("Sort by: Financial Year");
        colFinYear.setDBColumnName("V_FINANCIAL_YEAR");
        addColumn(colFinYear);

        //Setting properties for  Quarter.
        Column colQuarter=new Column("Quarter");
        colQuarter.setMethodName("getQuarter");
        colQuarter.setColumnWidth("20%");
        colQuarter.setIsHeaderLink(true);
        colQuarter.setHeaderLinkTitle("Sort by: Quarter");
        colQuarter.setDBColumnName("QUARTER");
        addColumn(colQuarter);

        //Setting properties for Acknowledgement No.
        Column colAckNum=new Column("Acknowledgement No.");
        colAckNum.setMethodName("getAckNbr");
        colAckNum.setColumnWidth("20%");
        colAckNum.setIsHeaderLink(true);
        colAckNum.setHeaderLinkTitle("Sort by: Acknowledgement No.");
        colAckNum.setDBColumnName("ACKNOWLEDGEMENT_NUMBER");
        addColumn(colAckNum);

        //Setting properties for  Acknowledgement Date.
        Column colAckDate=new Column("Acknowledgement Date");
        colAckDate.setMethodName("getAcknowledgeDate");
        colAckDate.setColumnWidth("15%");
        colAckDate.setIsHeaderLink(true);
        colAckDate.setHeaderLinkTitle("Sort by: Acknowledgement Date");
        colAckDate.setDBColumnName("ACKNOWLEDGEMENT_DATE");
        addColumn(colAckDate);

    } //end of setTableProperties()
}// end of TDSAcknowledgeTable class


