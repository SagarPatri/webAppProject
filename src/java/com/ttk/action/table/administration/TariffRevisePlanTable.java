/**
 * @ (#) TariffRevisePlanTable.java Oct 20, 2005
 * Project       : 
 * File          : TariffRevisePlanTable.java
 * Author        : Bhaskar Sandra
 * Company       : 
 * Date Created  : Oct 20, 2005
 *
 * @author       : Bhaskar Sandra 
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class TariffRevisePlanTable extends Table // implements TableObjectInterface,Serializable
{

	/* (non-Javadoc)
	 * @see com.ttk.action.table.Table#setTableProperties()
	 */
	public void setTableProperties() {

        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        Column columnRevision=new Column("Revision");   // Creating Column object for revision column
        columnRevision.setMethodName("getRevisionName");// set the method name of the column
        columnRevision.setColumnWidth("40%");           // Set the column width
        columnRevision.setIsLink(true);                 // Set Whether Column contain HyperLink
        columnRevision.setLinkTitle("Revision");        // Set the Link title
        columnRevision.setIsHeaderLink(true);           // Set Whether Column Header has HyperLink 
        columnRevision.setHeaderLinkTitle("Sort by: Revision"); //Set Header Link Title
        columnRevision.setDBColumnName("REVISION_NO");             // Set the Data base Column Name
        addColumn(columnRevision);                      // Adding columnRevision to the table
        
        Column columnStartDate=new Column("Start Date"); // Creating Column object for StartDate column
        columnStartDate.setMethodName("getStartDate");   // set the method name of the column
        columnStartDate.setColumnWidth("30%");           // Set the column width
        columnStartDate.setIsHeaderLink(true);           // Set Whether Column Header has HyperLink
        columnStartDate.setHeaderLinkTitle("Sort by: Start Date"); //Set Header Link Title
        columnStartDate.setDBColumnName("FROM_DATE");             // Set the Data base Column Name
        addColumn(columnStartDate);                      // Adding columnStartDate to the table
        
        Column columnEndDate =new Column("End Date");    // Creating Column object for EndDate column
        columnEndDate.setMethodName("getEndDate");       // set the method name of the column
        columnEndDate.setColumnWidth("30%");             // Set the column width
        columnEndDate.setIsHeaderLink(true);             // Set Whether Column Header has HyperLink
        columnEndDate.setHeaderLinkTitle("Sort by: End Date");   //Set Header Link Title
        columnEndDate.setDBColumnName("TO_DATE");               // Set the Data base Column Name
        addColumn(columnEndDate);                        //  Adding columnEndDAte to the table
        
        
        
        

	}

}
