/**
 * @ (#) PolicyAccountInfoTable.java Jul 26, 2007
 * Project      : TTK HealthCare Services
 * File         : PolicyAccountInfoTable.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 26, 2007
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.accountinfo;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class consists of the information requied to prepare grid table
 * for Policy Search screen in Account Info
 *
 */
public class PolicyAccountInfoTable extends Table
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
        colEnrollmentNo.setColumnWidth("14%");
        colEnrollmentNo.setIsLink(true);
        colEnrollmentNo.setIsHeaderLink(true);
        colEnrollmentNo.setHeaderLinkTitle("Sort by: Enrollment Id");
        colEnrollmentNo.setDBColumnName("tpa_enrollment_id");
        addColumn(colEnrollmentNo);

        //Setting properties for Policy No
        Column colPolicyNo=new Column("Policy No.");
        colPolicyNo.setMethodName("getPolicyNbr");
        colPolicyNo.setColumnWidth("12%");
        colPolicyNo.setImageName("getImageName");
        colPolicyNo.setImageTitle("getImageTitle");
        colPolicyNo.setIsLink(true);
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setLinkParamName("SecondLink");
        colPolicyNo.setHeaderLinkTitle("Sort by: Policy No.");
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);

        //Setting properties for Previous Policy No
        Column colPrevPolicyNo=new Column("Prev. Policy No.");
        colPrevPolicyNo.setMethodName("getPrevPolicyNbr");
        colPrevPolicyNo.setColumnWidth("12%");
        colPrevPolicyNo.setIsLink(true);
        colPrevPolicyNo.setIsHeaderLink(true);
        colPrevPolicyNo.setLinkParamName("ThirdLink");
        colPrevPolicyNo.setHeaderLinkTitle("Sort by: Prev. Policy No.");
        colPrevPolicyNo.setDBColumnName("PREV_POLICY_NUMBER");
        addColumn(colPrevPolicyNo);
        
        //Setting properties for Policy Start Date
        Column colPolicyStartDate=new Column("Policy Start Date");
        colPolicyStartDate.setMethodName("getListStartDate");
        colPolicyStartDate.setColumnWidth("10%");
        colPolicyStartDate.setIsHeaderLink(true);
        colPolicyStartDate.setHeaderLinkTitle("Sort by: Policy Start Date");
        colPolicyStartDate.setDBColumnName("EFFECTIVE_FROM_DATE");
        addColumn(colPolicyStartDate);
        
        //Setting properties for Policy End Date
        Column colPolicyEndDate=new Column("Policy End Date");
        colPolicyEndDate.setMethodName("getListEndDate");
        colPolicyEndDate.setColumnWidth("10%");
        colPolicyEndDate.setIsHeaderLink(true);
        colPolicyEndDate.setHeaderLinkTitle("Sort by: Policy End Date");
        colPolicyEndDate.setDBColumnName("EFFECTIVE_TO_DATE");
        addColumn(colPolicyEndDate);
        
        //Setting properties for Policy Type
        /*Column colPolicyType=new Column("Policy Type");
        colPolicyType.setMethodName("getPolicyTypeDesc");
        colPolicyType.setColumnWidth("16%");
        colPolicyType.setIsHeaderLink(true);
        colPolicyType.setHeaderLinkTitle("Sort by: Policy Type");
        colPolicyType.setDBColumnName("ENROL_TYPE_ID");
        addColumn(colPolicyType);*/

        //Setting properties for Group Name
        Column colGroupName=new Column("Group Name");
        colGroupName.setMethodName("getGroupName");
        colGroupName.setColumnWidth("12%");
        colGroupName.setIsHeaderLink(true);
        colGroupName.setHeaderLinkTitle("Sort by: Group Name");
        colGroupName.setDBColumnName("GROUP_NAME");
        addColumn(colGroupName);

        //Setting properties for Member Name
        Column colMemberName=new Column("Member Name");
        colMemberName.setMethodName("getMemberName");
        colMemberName.setColumnWidth("13%");
        colMemberName.setIsHeaderLink(true);
        colMemberName.setHeaderLinkTitle("Sort by: Member Name");
        colMemberName.setDBColumnName("MEM_NAME");
        addColumn(colMemberName);
        
        Column colEmiratesId=new Column("Emirates Id");
        colEmiratesId.setMethodName("getsEmiratesID");				
        colEmiratesId.setColumnWidth("14%");
        colEmiratesId.setIsHeaderLink(true);
        colEmiratesId.setHeaderLinkTitle("Sort by: Emirates Id");	
        colEmiratesId.setDBColumnName("emirate_id");				
        addColumn(colEmiratesId);

        //Setting properties for Preauth Exist
        Column colPreauthExist=new Column("Cashless Exist");
        colPreauthExist.setMethodName("getPreauthExist");
        colPreauthExist.setColumnWidth("8%");
        colPreauthExist.setIsHeaderLink(true);
        colPreauthExist.setHeaderLinkTitle("Sort by: Cashless Exist");
        colPreauthExist.setDBColumnName("PREAUTH_MADE");
        addColumn(colPreauthExist);
        
        //Setting properties for Claim Exist
        Column colClaimExist=new Column("Claim Exist");
        colClaimExist.setMethodName("getClaimExist");
        colClaimExist.setColumnWidth("8%");
        colClaimExist.setIsHeaderLink(true);
        colClaimExist.setHeaderLinkTitle("Sort by: Claim Exist");
        colClaimExist.setDBColumnName("CLAIM_MADE");
        addColumn(colClaimExist);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }//end of setTableProperties()
}//end of PolicyAccountInfoTable.java
