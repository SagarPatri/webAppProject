/**
 * @ (#) ProductPolicySyncTable.java Aug 3rd, 2007
 * Project      : TTK HealthCare Services
 * File         : ProductPolicySyncTable.java
 * Author       : Raghavendra T M
 * Company      : Span Info Tech
 * Date Created : Aug 3rd, 2007
 *
 * @author       : Raghavendra T M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Product details
 * 
 */
public class ProductPolicySyncTable extends Table{
    
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(100);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //Setting properties for Policy Number
        Column colPolicyNbr = new Column("Policy Number");
        colPolicyNbr.setMethodName("getPolicyNbr");
        colPolicyNbr.setColumnWidth("15%");
        colPolicyNbr.setIsHeaderLink(true);
        colPolicyNbr.setHeaderLinkTitle("Sort by: Policy Number");
        //colPolicyNbr.setIsLink(true);
        //colPolicyNbr.setLinkTitle("Edit Policy");
        colPolicyNbr.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNbr);
        
        //Setting properties for Previous Policy Number
        Column colPrevPolicyNbr = new Column("Previous Policy Number");
        colPrevPolicyNbr.setMethodName("getPrevPolicyNbr");
        colPrevPolicyNbr.setColumnWidth("15%");
        colPrevPolicyNbr.setIsHeaderLink(true);
        colPrevPolicyNbr.setHeaderLinkTitle("Sort by: Previous Policy Number");
        colPrevPolicyNbr.setDBColumnName("RENEWAL_POLICY_NUMBER");
        addColumn(colPrevPolicyNbr);
        
        
        //Setting properties for Group Name
        Column colGroupName =new Column("Group Name");
        colGroupName.setMethodName("getCorporateName");
        colGroupName.setColumnWidth("15%");
        colGroupName.setIsHeaderLink(true);
        colGroupName.setHeaderLinkTitle("Sort by: Group Name");
        colGroupName.setDBColumnName("GROUP_NAME");    
        addColumn(colGroupName);
        
        //Setting properties for Group Id
        Column colGroupId =new Column("Group Id");
        colGroupId.setMethodName("getGroupID");
        colGroupId.setColumnWidth("11%");
        colGroupId.setIsHeaderLink(true);
        colGroupId.setHeaderLinkTitle("Sort by: Group Id");
        colGroupId.setDBColumnName("GROUP_ID");    
        addColumn(colGroupId);
        
        //Setting properties for Valid From
        Column colValidFrom =new Column("Valid From");
        colValidFrom.setMethodName("getPolicyStartDate");
        colValidFrom.setColumnWidth("11%");
        colValidFrom.setIsHeaderLink(true);
        colValidFrom.setHeaderLinkTitle("Sort by: Valid From");
        colValidFrom.setDBColumnName("EFFECTIVE_FROM_DATE");    
        addColumn(colValidFrom);
        
        //Setting properties for Valid To
        Column colValidTo =new Column("Valid To");
        colValidTo.setMethodName("getPolicyEndDate");
        colValidTo.setColumnWidth("8%");
        colValidTo.setIsHeaderLink(true);
        colValidTo.setHeaderLinkTitle("Sort by: Valid To");
        colValidTo.setDBColumnName("EFFECTIVE_TO_DATE");    
        addColumn(colValidTo);
        
        Column colPolicyStatus =new Column("Policy Status");
        colPolicyStatus.setMethodName("getPolicyStatusID");
        colPolicyStatus.setColumnWidth("10%");
        colPolicyStatus.setIsHeaderLink(true);
        colPolicyStatus.setHeaderLinkTitle("Sort by: Policy Status");
        colPolicyStatus.setDBColumnName("description");    
        addColumn(colPolicyStatus);
        
        //Setting properties for Synch Date
        Column colSynchDate =new Column("Synch Date");
        colSynchDate.setMethodName("getRuleSynchDate");
        colSynchDate.setColumnWidth("10%");
        colSynchDate.setIsHeaderLink(true);
        colSynchDate.setHeaderLinkTitle("Sort by: Synch Date");
        colSynchDate.setDBColumnName("RULE_SYNCH_DATE");    
        addColumn(colSynchDate);
        //denial process
        Column colInsSynchDate =new Column("Insurer Synch Date");
        colInsSynchDate.setMethodName("getInsSynchDate");
        colInsSynchDate.setColumnWidth("10%");
        colInsSynchDate.setIsHeaderLink(true);
        colInsSynchDate.setHeaderLinkTitle("Sort by: Insurer Synch Date");
        colInsSynchDate.setDBColumnName("DEN_SYNC_DATE");    
        addColumn(colInsSynchDate);
        //denial process
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");       
        addColumn(colSelect); 
    }//end of setTableProperties()

}//end of ProductPolicySyncTable
