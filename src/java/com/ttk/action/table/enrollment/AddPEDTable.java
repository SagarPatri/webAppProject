/**
* @ (#) AddPEDTable.java Feb 6, 2006
* Project 		: TTK HealthCare Services
* File 			: AddPEDTable.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created 	: Feb 6, 2006
*
* @author 		: Pradeep R
* Modified by 	: 
* Modified date : 
* Reason 		: 
*/

package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class consists of the information requied to prepare grid table
 * for Add PED screens of Individual Policy,Ind. Policy as Group,
 * Corporate Policy and Non-Corporate Policy of enrollment module
 * 
 */

public class AddPEDTable extends Table {

	 /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
	public void setTableProperties() {
		setRowCount(50);
 		setCurrentPage(1);
    	setPageLinkCount(50);
    	
 		//Setting properties for Descripion
        Column colEnrollmentId=new Column("Descripion");
        colEnrollmentId.setMethodName("getDescription");
        colEnrollmentId.setColumnWidth("35%");	//width is changed for koc 1278
        colEnrollmentId.setIsLink(true);
        colEnrollmentId.setDBColumnName("DESCRIPTION");
        addColumn(colEnrollmentId);
        
        //Setting properties for Descripion
        Column colType=new Column("Type");
        colType.setMethodName("getPEDType");
        colType.setColumnWidth("10%");
        colType.setDBColumnName("PED_TYPE");
        addColumn(colType); 
        
        //added for koc 1278
        //Setting properties for Waiting Period
        Column colAilmentType=new Column("Ailment Type");
        colAilmentType.setMethodName("getAilmentTypeID");
        colAilmentType.setColumnWidth("10%");
        colAilmentType.setDBColumnName("AILMENT_GENERAL_TYPE_ID");
        addColumn(colAilmentType);
        //added for koc 1278

        //Setting properties for ICD Code
        Column colMembersName=new Column("ICD Code");
        colMembersName.setMethodName("getICDCode");
        colMembersName.setColumnWidth("10%");
        colMembersName.setDBColumnName("ICD_CODE");
        addColumn(colMembersName);
        
//      Setting properties for  Since(Month / Year)	        
        Column colYearr=new Column("Since (Month / Year)");
        colYearr.setMethodName("getDurationYrMonth");
        colYearr.setColumnWidth("20%");
        colYearr.setDBColumnName("DURATION");
        addColumn(colYearr);
        //Setting properties for Remarks	
        Column colRelationship=new Column("Remarks");
        colRelationship.setMethodName("getRemarks");
        colRelationship.setColumnWidth("20%");
        colRelationship.setDBColumnName("REMARKS");
        addColumn(colRelationship);

        //added for koc 1278
        //Setting properties for Waiting Period
        Column colWaitingPeriod=new Column("Waiting Period");
        colWaitingPeriod.setMethodName("getPersWtPeriod");
        colWaitingPeriod.setColumnWidth("10%");
        colWaitingPeriod.setDBColumnName("PERS_WT_PERIOD");
        addColumn(colWaitingPeriod);
        //added for koc 1278

        //Setting properties for check box
    	Column colSelect = new Column("Select");
    	colSelect.setComponentType("checkbox");
    	colSelect.setComponentName("chkopt");		
    	addColumn(colSelect); 
    }//end of void setTableProperties()
		

}//end of AddPEDTable class


