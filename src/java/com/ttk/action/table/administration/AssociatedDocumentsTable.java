package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class AssociatedDocumentsTable extends Table
{
	private static final long serialVersionUID = 1L;
	/**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
    	setRowCount(25);
        setCurrentPage(1);
        setPageLinkCount(25);
        //Setting properties for Link Description
        Column colDocumentName=new Column("Document Name");
        colDocumentName.setMethodName("getDocName");
        colDocumentName.setColumnWidth("45%");
        colDocumentName.setIsLink(true);
        colDocumentName.setIsHeaderLink(true);
        colDocumentName.setHeaderLinkTitle("Sort by: Document Name");
        colDocumentName.setDBColumnName("DOC_NAME");
        addColumn(colDocumentName);
        
        //Setting properties for Type
        Column colDocumentPath = new Column("Document Path");
        colDocumentPath.setMethodName("getDocPath");
        colDocumentPath.setColumnWidth("45%");
        colDocumentPath.setIsHeaderLink(true);
        colDocumentPath.setHeaderLinkTitle("Sort by: Document Path");
        colDocumentPath.setDBColumnName("DOC_PATH");
        addColumn(colDocumentPath);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
    }//end of setTableProperties()    
}//end of AssociatedDocumentsTable
