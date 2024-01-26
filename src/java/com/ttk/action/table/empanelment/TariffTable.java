/**
 * @ (#) TariffTable.java Oct 21, 2005
 * Project       : TTK HealthCare Services
 * File          : TariffTable.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Oct 21, 2005
 *
 * @author       : Srikanth H M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of Hospital Tariff table
 */
public class TariffTable extends Table // implements TableObjectInterface,Serializable 
{
	/**
	 * This creates the columnproperties objects for each and 
	 * every column and adds the column object to the table
	 */
	
	public void setTableProperties()
	{
		setRowCount(20);
		setCurrentPage(1);
		setPageLinkCount(20);
		//Setting properties for Associated To
		Column colAssociateTo=new Column("Associated To");
		colAssociateTo.setMethodName("getCompanyName"); 
		colAssociateTo.setColumnWidth("50%");		
		colAssociateTo.setIsHeaderLink(true);
		colAssociateTo.setHeaderLinkTitle("Sort by: Associated To");
		colAssociateTo.setDBColumnName("ASSOCIATED_TO");
		addColumn(colAssociateTo);		
				
		//Setting properties for product/ Scheme No
		Column colPolicyNo=new Column("Product / Scheme No.");
		colPolicyNo.setMethodName("getProdPolicyNumber");
		colPolicyNo.setColumnWidth("50%");
		//colPolicyNo.setDBColumnName("INS_SEQ_ID");//GIVE PROPER COLUMN NAME
		//colPolicyNo.setIsHeaderLink(true);
		//colPolicyNo.setHeaderLinkTitle("Sort by: Product / Scheme No.");
		addColumn(colPolicyNo);
		
		//ADD IMAGE THINGS OVER HERE
		//Setting properties for image Revision History
		Column colImage1 = new Column("");
		colImage1.setIsImage(true);
		colImage1.setIsImageLink(true);
		colImage1.setImageName("getImageName");
        colImage1.setImageTitle("getImageTitle");
        addColumn(colImage1);
	}
}
