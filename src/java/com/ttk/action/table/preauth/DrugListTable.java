/**
 * @ (#) DrugListTable.java June 18, 2015
 * Project      : Project-X
 * File         : DrugListTable.java
 * Author       : Nagababu K
 * Company      : Vidal Health TPA Pvt. Ltd.
 * Date Created : June 18, 2015
 *
 * @author       :  Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class DrugListTable extends Table
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Code
        Column colActivityCode=new Column("Drug Code");
        colActivityCode.setMethodName("getDrugCode");
        colActivityCode.setColumnWidth("10%");
        colActivityCode.setIsLink(true);
        colActivityCode.setIsHeaderLink(true);      
        colActivityCode.setHeaderLinkTitle("Sort DRUG Code");
        colActivityCode.setDBColumnName("DRUG");
        addColumn(colActivityCode);
        
        Column colDescription=new Column("Description");
        colDescription.setMethodName("getDrugDesc");
        colDescription.setColumnWidth("90%");
        colDescription.setDBColumnName("DRUG_DESCRIPTION");
        addColumn(colDescription);
/*
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);*/
    }// end of public void setTableProperties()

}// end of DrugListTable class
