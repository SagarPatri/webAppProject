/**
 * @ (#) OnlineMemberTable.java Jan 14th, 2008
 * Project       : TTK HealthCare Services
 * File          : OnlineMemberTable.java
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : Jan 14th, 2008
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class OnlineMemberTable extends Table
{
	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	public void setTableProperties()
    {
        setRowCount(200);
        setCurrentPage(1);
        setPageLinkCount(50);

        //Setting properties for Member Name
        Column colMemberName = new Column("Member Name");
        colMemberName.setMethodName("getName");
        colMemberName.setColumnWidth("15%");
        colMemberName.setIsHeaderLink(false);
        colMemberName.setIsLink(true);
        colMemberName.setDBColumnName("MEM_NAME");
        addColumn(colMemberName);
        
        //Setting properties for Enrollment ID
        Column colEnrollmentID=new Column("Enrollment ID");
        colEnrollmentID.setMethodName("getEnrollmentID");
        colEnrollmentID.setColumnWidth("21%");
        colEnrollmentID.setIsHeaderLink(false);
        colEnrollmentID.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentID);
        
        //Setting properties for Member Type
        Column colMemberType=new Column("Member Type");
        colMemberType.setMethodName("getMemberType");
        colMemberType.setColumnWidth("15%");
        colMemberType.setIsHeaderLink(false);
        colMemberType.setDBColumnName("MEMBER_TYPE");
        addColumn(colMemberType);

        //Setting properties for Relationship
        Column colRelationship=new Column("Relationship");
        colRelationship.setMethodName("getRelationDesc");
        colRelationship.setColumnWidth("15%");
        colRelationship.setIsHeaderLink(false);
        colRelationship.setDBColumnName("RELATIONSHIP");
        addColumn(colRelationship);
        
        //Setting properties for Date of Birth
        Column colAge=new Column("Age");
        colAge.setMethodName("getAge");
        colAge.setColumnWidth("4%");
        colAge.setIsHeaderLink(false);
        colAge.setDBColumnName("MEM_AGE");
        addColumn(colAge);
        
        //Setting properties for Gender
        Column colGender=new Column("Gender");
        colGender.setMethodName("getGenderDescription");
        colGender.setColumnWidth("7%");
        colGender.setIsHeaderLink(false);
        colGender.setDBColumnName("GENDER");
        addColumn(colGender);
        
        //Setting properties for Date of Birth
        Column colDateOfBirth=new Column("Date of Birth");
        colDateOfBirth.setMethodName("getWebDateOfBirth");
        colDateOfBirth.setColumnWidth("8%");
        colDateOfBirth.setIsHeaderLink(false);
        colDateOfBirth.setDBColumnName("MEM_DOB");
        addColumn(colDateOfBirth);
        
        //Setting properties for Total Sum Insured
        Column colSumInsured=new Column("Total Sum Insured");
        colSumInsured.setMethodName("getTotalSumInsured");
        colSumInsured.setColumnWidth("8%");
        colSumInsured.setIsHeaderLink(false);
        colSumInsured.setDBColumnName("MEM_TOT_SUM_INSURED");
        addColumn(colSumInsured);
        
		// Change added for KOC1227K
        //Setting properties for image Additional Sum Insured
        Column colImage1 = new Column("Edit Sum Insured");
        colImage1.setColumnWidth("5%");
        colImage1.setIsImage(true);
        colImage1.setIsImageLink(true);
        colImage1.setImageName("getImageName");
        colImage1.setImageTitle("getImageTitle");
        addColumn(colImage1);
        
		// Change added for KOC1227K
        //Setting properties for Delete image
        Column colImage2 = new Column("Cancel");
        colImage2.setColumnWidth("5%");
        colImage2.setIsImage(true);
        colImage2.setIsImageLink(true);
        colImage2.setImageName("getDeleteImageName");
        colImage2.setImageTitle("getDeleteImageTitle");
        addColumn(colImage2);
}//end of public void setTableProperties()

}//end of OnlineMemberTable
