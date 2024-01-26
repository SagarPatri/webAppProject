package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class PreAuthIntimationTable extends Table 
{
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
        //Setting properties for Description
        Column colIntimationID = new Column("Intimation ID");
        colIntimationID.setMethodName("getIntimationNbr");
        colIntimationID.setColumnWidth("14%");
        colIntimationID.setIsLink(true);
        colIntimationID.setDBColumnName("PAT_INTIMATION_ID");
        addColumn(colIntimationID);
        
        //Setting properties for Enrollment ID
        Column colEnrollmentID=new Column("Vidal Healthcare ID");
        colEnrollmentID.setMethodName("getEnrollmentID");
        colEnrollmentID.setColumnWidth("25%");
        colEnrollmentID.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentID);
        
        //Setting properties for Membername
        Column colMemberName = new Column("Member Name");
        colMemberName.setMethodName("getMemName");
        colMemberName.setColumnWidth("20%");
        colMemberName.setDBColumnName("MEM_NAME");
        addColumn(colMemberName);
        
        //Setting properties for Gender
        Column colGender = new Column("Gender");
        colGender.setMethodName("getGenderDesc");
        colGender.setColumnWidth("7%");
        colGender.setDBColumnName("GENDER");
        addColumn(colGender);
        
        // Setting properties for Status
        Column colStatus = new Column("Status");
        colStatus.setMethodName("getStatusDesc");
        colStatus.setColumnWidth("14%");
        colStatus.setDBColumnName("STATUS_DESC");
        addColumn(colStatus);
        
        //Setting properties for Intimation Sent Date
        Column colSentDate = new Column("Intimation Date / Time");
        colSentDate.setMethodName("getIntimationDate");
        colSentDate.setColumnWidth("20%");
        colSentDate.setDBColumnName("INTIMATION_GENERATED_DATE");
        addColumn(colSentDate);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }//end of setTableProperties() 
}//end of PreAuthIntimationTable
