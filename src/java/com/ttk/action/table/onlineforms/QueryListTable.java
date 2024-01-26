package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class QueryListTable extends Table
{
	private static final long serialVersionUID = 1L;
	/**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
    	setRowCount(100);
        setCurrentPage(1);
        setPageLinkCount(10);
        //Setting properties for Request No
        Column colIntimationID = new Column("Request No.");
        colIntimationID.setMethodName("getRequestID");
        colIntimationID.setColumnWidth("25%");
        colIntimationID.setIsLink(true);
        colIntimationID.setDBColumnName("REQUEST_ID");
        addColumn(colIntimationID);
        
        //Setting properties for Latest Request Date
        Column colEnrollmentID=new Column("Latest Requested Date");
        colEnrollmentID.setMethodName("getAssLatestReqDate");
        colEnrollmentID.setColumnWidth("25%");
        colEnrollmentID.setDBColumnName("LAST_SUBMITTED_DATE");
        addColumn(colEnrollmentID);
        
        //Setting properties for Status
        Column colMemberName = new Column("Status");
        colMemberName.setMethodName("getStatus");
        colMemberName.setColumnWidth("25%");
        colMemberName.setDBColumnName("STATUS");
        addColumn(colMemberName);
        
        //Setting properties for TTK Responded Date
        Column colGender = new Column("Vidal Healthcare Responded Date");
        colGender.setMethodName("getTTKRespondedDate");
        colGender.setColumnWidth("25%");
        colGender.setDBColumnName("CLARIFIED_DATE");
        addColumn(colGender);
        
    }//end of setTableProperties() 
}//end of QueryListTable
