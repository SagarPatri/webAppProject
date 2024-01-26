/**
 * @ (#) OtherPolicyTable.java Feb 6th, 2006
 * Project      : TTK HealthCare Services
 * File         : OtherPolicyTable.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : Feb 6th, 2006
 *
 * @author       :  Krupa J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class OtherPolicyTable extends Table
{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(50);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        
        //Setting properties for image Associate
        Column colImage1 = new Column("");
        colImage1.setIsImage(true);
        colImage1.setImageName("getImageName");
        colImage1.setImageTitle("getImageTitle");
        addColumn(colImage1);
        
        //Setting properties for Policy No.
        Column colGroupId=new Column("Policy No.");
        colGroupId.setMethodName("getPolicyNbr");
        colGroupId.setColumnWidth("50%");
        colGroupId.setDBColumnName("POLICY_NUMBER");
        addColumn(colGroupId);

        //Setting properties for Enrollment Id. 
        Column colGroupName=new Column("Enrollment Id.");
        colGroupName.setMethodName("getEnrollmentNbr");
        colGroupName.setColumnWidth("50%");
        colGroupName.setDBColumnName("TPA_ENROLLMENT_NUMBER");
        addColumn(colGroupName);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }// end of setTableProperties() 
}//end of OtherPolicyTable.java
