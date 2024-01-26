package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class QuryInfoTable extends Table
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
                
        //Setting properties for Latest Request Date
        Column colEnrollmentID=new Column("Latest Requested Date");
        colEnrollmentID.setMethodName("getAssLatestReqDate");
        colEnrollmentID.setColumnWidth("21%");
        colEnrollmentID.setDBColumnName("SUBMITTED_DATE");
        addColumn(colEnrollmentID);
        
        //Setting properties for Questions
        Column colGender = new Column("Questions");
        colGender.setMethodName("getQueryDesc");
        colGender.setIsLink(true);
        colGender.setColumnWidth("31%");
        colGender.setDBColumnName("QUERY_PART");
        addColumn(colGender);
        
        //Setting properties for Status
        Column colMemberName = new Column("Status");
        colMemberName.setMethodName("getStatus");
        colMemberName.setColumnWidth("14%");
        colMemberName.setDBColumnName("STATUS");
        addColumn(colMemberName);
        
        //Setting Properties for FeedBackType
        Column colFeedBackType= new Column("FeedBack Type");
        colFeedBackType.setMethodName("getFeedBackDesc");
        colFeedBackType.setColumnWidth("10%");
        colFeedBackType.setDBColumnName("FEEDBACK_QUERY_TYPE");
        addColumn(colFeedBackType);
        
        //Setting Properties for TTKResponse
        Column colFeedBackStatus= new Column("Vidal Healthcare Response");
        colFeedBackStatus.setMethodName("getFeedbackStatus");
        colFeedBackStatus.setColumnWidth("13%");
        colFeedBackStatus.setDBColumnName("FEEDBACK_STATUS");
        addColumn(colFeedBackStatus);
        
        //setting properties for Emp. FeedBack Status
        Column colEmpFeedBackStatus= new Column("Emp. FeedBack Status");
        colEmpFeedBackStatus.setMethodName("getQueryFeedbackStatusID");
        colEmpFeedBackStatus.setColumnWidth("11%");
        colEmpFeedBackStatus.setDBColumnName("FEEDBACK_STATUS_GEN_TYPE_ID");
        addColumn(colEmpFeedBackStatus);
    }//end of setTableProperties() 

}//end of QuryInfoTable class
