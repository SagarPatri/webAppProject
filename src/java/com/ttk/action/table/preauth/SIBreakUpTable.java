/**

* @ (#) SIBreakUpTable.java September 15, 2009
* Project       : TTK HealthCare Services
* File          : SIBreakUpTable.java
* Author        : Navin Kumar R
* Company       : Span Systems Corporation
* Date Created  : September 15, 2009

* @author       : 
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class SIBreakUpTable  extends Table
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
        Column colInsComp=new Column("Breakup");
        colInsComp.setMethodName("getRowNbr");
        colInsComp.setColumnWidth("10%");
        colInsComp.setDBColumnName("RNO");
        addColumn(colInsComp);

        //Setting properties for Financial Year.
        Column colFinYear=new Column("Sum Insured");
        colFinYear.setMethodName("getMemSumInsured");
        colFinYear.setColumnWidth("30%");
        colFinYear.setDBColumnName("MEM_SUM_INSURED");
        addColumn(colFinYear);

        //Setting properties for  Quarter.
        Column colQuarter=new Column("Bonus");
        colQuarter.setMethodName("getMemBonus");
        colQuarter.setColumnWidth("30%");
        colQuarter.setDBColumnName("MEM_BONUS_AMOUNT");
        addColumn(colQuarter);

        //Setting properties for Acknowledgement No.
        Column colAckNum=new Column("Eff dt/ Enh dt");
        colAckNum.setMethodName("getPolSIEffDate");
        colAckNum.setColumnWidth("30%");
        colAckNum.setDBColumnName("EFFECTIVE_DATE");
        addColumn(colAckNum);
    } //end of setTableProperties()
}
