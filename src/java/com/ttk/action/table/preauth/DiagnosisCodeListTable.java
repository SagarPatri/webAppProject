/**
 * @ (#) ActivityCodeTable.java June 18, 2015
 * Project      : Project-X
 * File         : ActivityCodeTable.java
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


public class DiagnosisCodeListTable extends Table
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
        Column colDiagnosisCode=new Column("Diagnosis Code");
        colDiagnosisCode.setMethodName("getIcdCode");
        colDiagnosisCode.setColumnWidth("10%");
        colDiagnosisCode.setIsLink(true);
        colDiagnosisCode.setIsHeaderLink(true);      
        colDiagnosisCode.setHeaderLinkTitle("Sort by:Diagnosis Code");
        colDiagnosisCode.setDBColumnName("ICD_CODE");
        addColumn(colDiagnosisCode);
        
        Column colDescription=new Column("Description");
        colDescription.setMethodName("getAilmentDescription");
        colDescription.setColumnWidth("90%");
        colDescription.setDBColumnName("LONG_DESC");
        addColumn(colDescription);
/*
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);*/
    }// end of public void setTableProperties()

}// end of ActivityCodeTable class
