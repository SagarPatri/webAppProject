/**
 * @ (#) AssociateCertificateAction.java May 06, 2010
 * Project      : TTK HealthCare Services
 * File         :AssociateCertificateAction.java
 * Author       : Swaroop Kaushik D.S
 * Company      : Span Systems Corporation
 * Date Created : May 06, 2010
 *
 * @author       : Swaroop Kaushik D.S
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class AssociatedCertificatesTable extends Table{

	/**
     * This creates the column properties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
    	setRowCount(25);
        setCurrentPage(1);
        setPageLinkCount(25);
        //Setting properties for Link Description
        Column colDescription=new Column("Description");
        colDescription.setMethodName("getDescription");
        colDescription.setColumnWidth("45%");
        colDescription.setIsLink(true);
        colDescription.setIsHeaderLink(true);
        colDescription.setHeaderLinkTitle("Sort by: Description");
        colDescription.setDBColumnName("DESCRIPTION");
        addColumn(colDescription);
        
        //Setting properties for Financial Year
        Column colFinancialYear = new Column("Financial Year");
        colFinancialYear.setMethodName("getStrFinancialYear");
        colFinancialYear.setColumnWidth("45%");
        colFinancialYear.setIsHeaderLink(true);
        colFinancialYear.setHeaderLinkTitle("Sort by: Financial Year");
        colFinancialYear.setDBColumnName("FINANCIAL_YEAR");
        addColumn(colFinancialYear);
        
        //Setting properties for image  
        Column colImage = new Column("");
        colImage.setIsImage(true);
        colImage.setIsImageLink(true);
        colImage.setImageName("getImageName");
        colImage.setImageTitle("getImageTitle");
        addColumn(colImage);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);        
    }//end of setTableProperties()
	
}//end of AssociatedCertificatesTable
