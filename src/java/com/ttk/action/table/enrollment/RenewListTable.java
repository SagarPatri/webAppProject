/**
* @ (#) RenewListTable.java Feb 3, 2006
* Project 		: TTK HealthCare Services
* File 			: RenewListTable.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created 	: Feb 3, 2006
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
 * for Renew Member screens of Individual Policy,Ind. Policy as Group,
 * Corporate Policy and Non-Corporate Policy of enrollment module
 * 
 */

public class RenewListTable extends Table
{
	 /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
	 public void setTableProperties()
	    {
	       
	 		setRowCount(50);
	 		setCurrentPage(1);
        	setPageLinkCount(50);
        	
	 		//Setting properties for Enrollment Id
	        Column colEnrollmentId=new Column("Enrollment Id");
	        colEnrollmentId.setMethodName("getEnrollmentID");
	        colEnrollmentId.setColumnWidth("25%");
	        colEnrollmentId.setDBColumnName("TPA_ENROLLMENT_ID");
	        addColumn(colEnrollmentId);
	        
	        //Setting properties for Members Name
	        Column colMembersName=new Column("Members Name");
	        colMembersName.setMethodName("getName");
	        colMembersName.setColumnWidth("25%");
	        colMembersName.setDBColumnName("MEM_NAME");
	        addColumn(colMembersName);
	        //Setting properties for Gender	        
	        Column colGender=new Column("Gender");
	        colGender.setMethodName("getGenderTypeID");
	        colGender.setColumnWidth("10%");
	        colGender.setDBColumnName("GENDER");
	        addColumn(colGender);
	        //Setting properties for Relationship	
	        Column colRelationship=new Column("Relationship");
	        colRelationship.setMethodName("getRelationDesc");
	        colRelationship.setColumnWidth("15%");
	        colRelationship.setDBColumnName("RELSHIP_DESCRIPTION");
	        addColumn(colRelationship);
	        //Setting properties for Age(yrs)	
	        Column colAge=new Column("Age(yrs)");
	        colAge.setMethodName("getFormattedAge");
	        colAge.setColumnWidth("10%");
	        colAge.setDBColumnName("MEM_AGE");
	        addColumn(colAge);
	        //Setting properties for DOB	
	        Column colDOB=new Column("DOB");
	        colDOB.setMethodName("getDOB");
	        colDOB.setColumnWidth("15%");
	        colDOB.setDBColumnName("MEM_DOB");
	        addColumn(colDOB);
	        
	        //Setting properties for check box
	        Column colCovered = new Column("Covered");
	        colCovered.setComponentType("checkbox");
	        colCovered.setComponentName("chkopt");
	        addColumn(colCovered);
	    }//end of void setTableProperties()
	        
}//end of RenewListTable extends Table


