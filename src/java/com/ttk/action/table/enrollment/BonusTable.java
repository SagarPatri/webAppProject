/**
 * @ (#) BonusTable.java Feb 10, 2006
 * Project       : TTK HealthCare Services
 * File          : BonusTable.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 10, 2006
 *
 * @author       : Srikanth H M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class BonusTable  extends Table {

	/**
    * This creates the columnproperties objects for each and 
    * every column and adds the column object to the table
    */
	public void setTableProperties() {
		setRowCount(100);
		setCurrentPage(1);
		setPageLinkCount(100);
   	
		//Setting properties for Descripion
       Column colEnrollmentId=new Column("Date");
       colEnrollmentId.setMethodName("getEffectDate");
       colEnrollmentId.setColumnWidth("20%");
       colEnrollmentId.setDBColumnName("POLICY_DATE");
       colEnrollmentId.setIsLink(true);
       addColumn(colEnrollmentId);
       
       //Setting properties for ICD Code
       Column colMembersName=new Column("Sum Insured ");
       colMembersName.setMethodName("getMemSumInsured");
       colMembersName.setColumnWidth("30%");
       colMembersName.setDBColumnName("MEM_SUM_INSURED");
       addColumn(colMembersName);
       
       //Setting properties for Duration of Illness	        
       Column colGender=new Column("Bonus (%)");
       colGender.setMethodName("getBonus");
       colGender.setColumnWidth("25%");
       colGender.setDBColumnName("MEM_BONUS_PERCENT");
       addColumn(colGender);
       
       //Setting properties for Remarks	
       Column colRelationship=new Column("Bonus Amount ");
       colRelationship.setMethodName("getBonusAmt");
       colRelationship.setColumnWidth("25%");
       colRelationship.setDBColumnName("MEM_BONUS_AMOUNT");
       addColumn(colRelationship);
     
       //Setting properties for check box
   	Column colSelect = new Column("Select");
   	colSelect.setComponentType("checkbox");
   	colSelect.setComponentName("chkopt");		
   	addColumn(colSelect); 
   }//end of void setTableProperties()
}
