/**
 * @ (#) PolicyGroupTableFinance.javaFeb 2, 2006
 * Project      : TTK HealthCare Services
 * File         : PolicyGroupTableFinance.java
 * Author       : Kishor Kumar S H
 * Company      : RCS
 * Date Created : July 15 2015
 *
 * @author       :  Kishor Kumar S H
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class PolicyGroupTableFinance extends Table
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
        
        //Setting properties for Group Id.
        Column colPolicyNo=new Column("Policy No");
        colPolicyNo.setMethodName("getPolicyNo");
        colPolicyNo.setColumnWidth("20%");
        colPolicyNo.setIsLink(true);
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setHeaderLinkTitle("Sort by: Policy No.");
        colPolicyNo.setDBColumnName("POLICY_NO");
        addColumn(colPolicyNo);
        
        
      //Setting properties for Group Id.
        Column colGroupId=new Column("Group Id");
        colGroupId.setMethodName("getGroupID");
        colGroupId.setColumnWidth("20%");
        //colGroupId.setIsLink(true);
        colGroupId.setIsHeaderLink(true);
        colGroupId.setHeaderLinkTitle("Sort by: Group Id.");
        colGroupId.setDBColumnName("GROUP_ID");
        addColumn(colGroupId);
        

        //Setting properties for Group Name 
        Column colGroupName=new Column("Group Name");
        colGroupName.setMethodName("getGroupName");
        colGroupName.setColumnWidth("40%");
        colGroupName.setIsHeaderLink(true);
        colGroupName.setHeaderLinkTitle("Sort by: Group Name.");
        colGroupName.setDBColumnName("GROUP_NAME");
        addColumn(colGroupName);
        
        //Setting properties forVidal Health TPABranch.
        Column colTtkBranch=new Column("Vidal Health Branch");
        colTtkBranch.setMethodName("getBranchName");
        colTtkBranch.setColumnWidth("20%");
        colTtkBranch.setIsHeaderLink(true);
        colTtkBranch.setHeaderLinkTitle("Sort by: Vidal Health Branch.");
        colTtkBranch.setDBColumnName("OFFICE_NAME");
        addColumn(colTtkBranch);
    }// end of setTableProperties() 
}//end of PolicyGroupTableFinance.java
