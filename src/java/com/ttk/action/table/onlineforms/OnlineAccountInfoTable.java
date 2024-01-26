/**
 * @ (#) OnlineAccountInfoTable.java March 24, 2008
 * Project      : TTK HealthCare Services
 * File         : OnlineAccountInfoTable.java
 * Author       : Balakrishna E
 * Company      : Span Systems Corporation
 * Date Created : March 24, 2008
 *
 * @author       :  Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class consists of the information requied to prepare grid table
 * for Policy Search screen in Account Info
 *
 */
public class OnlineAccountInfoTable extends Table
{
    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //The order of the column should remain as same as here,if any new colum needs
        //to be added then add at the end

        //Setting properties for Enrollment No.
        Column colEnrollmentNo=new Column("Enrollment Id");
        colEnrollmentNo.setMethodName("getEnrollmentID");
        colEnrollmentNo.setColumnWidth("19%");
        colEnrollmentNo.setIsLink(true);
        colEnrollmentNo.setIsHeaderLink(true);
        colEnrollmentNo.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentNo.setDBColumnName("TPA_ENROLLMENT_ID");
        addColumn(colEnrollmentNo);

        //Setting properties for Scheme No
        Column colPolicyNo=new Column("Policy No.");
        colPolicyNo.setMethodName("getPolicyNbr");
        colPolicyNo.setColumnWidth("17%");
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setHeaderLinkTitle("Sort by: Policy No.");
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);

        //Setting properties for Group Name
        Column colGroupName=new Column("Group Name");
        colGroupName.setMethodName("getGroupName");
        colGroupName.setColumnWidth("15%");
        colGroupName.setIsHeaderLink(true);
        colGroupName.setHeaderLinkTitle("Sort by: Group Name");
        colGroupName.setDBColumnName("GROUP_NAME");
        addColumn(colGroupName);

        //Setting properties for Member Name
        Column colMemberName=new Column("Member Name");
        colMemberName.setMethodName("getMemberName");
        colMemberName.setColumnWidth("16%");
        colMemberName.setIsHeaderLink(true);
        colMemberName.setHeaderLinkTitle("Sort by: Member Name");
        colMemberName.setDBColumnName("MEM_NAME");
        addColumn(colMemberName);
        
//      Setting properties for Healthcare Compnay Code
        Column colInsCompnayCode=new Column("Insurance Compnay Code");
        colInsCompnayCode.setMethodName("getInsCompCodeNbr");
        colInsCompnayCode.setColumnWidth("16%");
        colInsCompnayCode.setIsHeaderLink(true);
        colInsCompnayCode.setHeaderLinkTitle("Sort by: Insurance Compnay Code");
        colInsCompnayCode.setDBColumnName("INS_COMP_CODE_NUMBER");
        addColumn(colInsCompnayCode);

        
    }//end of setTableProperties()
}//end of PolicyAccountInfoTable.java
