/**
 * @ (#) PreAuthTable.javaMay 5, 2006
 * Project      : TTK HealthCare Services
 * File         : PreAuthTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : May 5, 2006
 *
 * @author       :  Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class PreAuthShortfallsTable extends Table
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
        setRowCount(11);
        setCurrentPage(1);
        setPageLinkCount(10);       
        
        //Setting properties for shortfall no.0
        Column colShortFallNo = new Column("Shortfall No.");
        colShortFallNo.setMethodName("getShortfallNo");
        colShortFallNo.setColumnWidth("30%");
        colShortFallNo.setIsLink(true);
        colShortFallNo.setIsHeaderLink(true);
        colShortFallNo.setLinkTitle("Edit ShortFall");
        colShortFallNo.setHeaderLinkTitle("Sort by: Shortfall No.");
        colShortFallNo.setDBColumnName("SHORTFALL_ID");
        addColumn(colShortFallNo);
        
        Column colPreAuthNo=new Column("Pre-Auth No.");
        colPreAuthNo.setMethodName("getPreAuthNo");
        colPreAuthNo.setColumnWidth("20%");
        //colPreAuthNo.setIsLink(true);
        colPreAuthNo.setIsHeaderLink(true);
        //colPreAuthNo.setImageName("getShortfallImageName");
        //colPreAuthNo.setImageTitle("getShortfallImageTitle");
		//colPreAuthNo.setImageName2("getInvImageName"); 
        //colPreAuthNo.setImageTitle2("getInvImageTitle");
        colPreAuthNo.setHeaderLinkTitle("Sort by: Pre-Auth No.");
        colPreAuthNo.setDBColumnName("PRE_AUTH_NUMBER");
        addColumn(colPreAuthNo);
        
        //Setting properties for  Enrollment Id.
        Column colEnrollmentId=new Column("Enrollment Id");
        colEnrollmentId.setMethodName("getEnrollmentID");
        colEnrollmentId.setColumnWidth("20%");
        colEnrollmentId.setIsHeaderLink(true);
        colEnrollmentId.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentId);
        
        //Setting properties for status6
        Column colStatus = new Column("Status");
        colStatus.setMethodName("getStatusDesc");
        colStatus.setColumnWidth("20%");
        colStatus.setIsHeaderLink(true);
        colStatus.setHeaderLinkTitle("Sort by: Status");
        colStatus.setDBColumnName("SRTFLL_STATUS");
        addColumn(colStatus);      
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setColumnWidth("1%");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }// end of public void setTableProperties()

}// end of PreAuthTable class
