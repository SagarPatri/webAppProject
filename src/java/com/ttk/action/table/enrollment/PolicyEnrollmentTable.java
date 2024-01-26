/**
 * @ (#) PolicyEnrollmentTable.java Feb 1, 2006
 * Project      : TTK HealthCare Services
 * File         : PolicyEnrollmentTable
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Feb 1, 2006
 *
 * @author       :  Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class consists of the information requied to prepare grid table
 * for Policy Search screens of Individual Policy,Ind. Policy as Group,
 * Corporate Policy and Non-Corporate Policy of enrollment module
 * 
 */
public class PolicyEnrollmentTable extends Table
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
        
        //The order of the column should remain as same as here, if any new colum needs to be added then add at the end
        
        //Setting properties for Scheme No 
        Column colPolicyNo=new Column("Policy No.");
        colPolicyNo.setMethodName("getPolicyNbr");
        colPolicyNo.setColumnWidth("9%");
        colPolicyNo.setImageName("getImageName");
        colPolicyNo.setImageTitle("getImageTitle");
        colPolicyNo.setIsLink(true);
        colPolicyNo.setIsHeaderLink(true);
        colPolicyNo.setHeaderLinkTitle("Sort by: Policy No.");
        colPolicyNo.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNo);
        
        //Setting properties for Policy Category 
        Column colPolicyCategory=new Column("Policy Category");
        colPolicyCategory.setMethodName("getPolicyCategory");
        colPolicyCategory.setColumnWidth("5%");
        colPolicyCategory.setIsHeaderLink(true);
        colPolicyCategory.setHeaderLinkTitle("Sort by: Policy Category.");
        colPolicyCategory.setDBColumnName("CLASSIFICATION");
        addColumn(colPolicyCategory);
        
      //Start Ak
		//Setting properties for Product_Name
		Column colsProductName = new Column("Product Name");
		colsProductName.setMethodName("getProductName");
		colsProductName.setColumnWidth("12%");
		colsProductName.setIsHeaderLink(true);
		colsProductName.setHeaderLinkTitle("Sort by Product_Name");
		colsProductName.setDBColumnName("Product_name");
		addColumn(colsProductName);
		
		//Setting properties for getProduct_Authority
		Column colProductAuthority = new Column("Product Authority");
		colProductAuthority.setMethodName("getProductAuthority");
		colProductAuthority.setColumnWidth("13%");
		colProductAuthority.setIsHeaderLink(true);
		colProductAuthority.setHeaderLinkTitle("Sort by Product_Authority");
		colProductAuthority.setDBColumnName("Authority_type");
		addColumn(colProductAuthority);
		
		//End Ak
        
        //Setting properties for Enrollment No.
        Column colEnrollmentNo=new Column("Enrollment No.");
        colEnrollmentNo.setMethodName("getEnrollmentNbr");
        colEnrollmentNo.setColumnWidth("9%");
        colEnrollmentNo.setIsLink(true);
        colEnrollmentNo.setIsHeaderLink(true);
        colEnrollmentNo.setLinkParamName("SecondLink");
        colEnrollmentNo.setHeaderLinkTitle("Sort by: Enrollment No.");
        colEnrollmentNo.setDBColumnName("TPA_ENROLLMENT_NUMBER");
        addColumn(colEnrollmentNo);
        
        
        
        Column dhpoMemberId=new Column("Member ID.");
        dhpoMemberId.setMethodName("getDhpoMemberId");
        dhpoMemberId.setColumnWidth("9%");
        dhpoMemberId.setIsHeaderLink(true);
        dhpoMemberId.setLinkParamName("SecondLink");
        dhpoMemberId.setHeaderLinkTitle("Sort by: Enrollment No.");
        dhpoMemberId.setDBColumnName("DHPO_MEMBER_ID");
        addColumn(dhpoMemberId);
        
        
        

        //Setting properties for Gn Member Id.
//        Column colVidalMemberId=new Column("Vipul Bettercare Member ID");
//        colVidalMemberId.setMethodName("getVidalMemId");
//        colVidalMemberId.setColumnWidth("10%");
//        colVidalMemberId.setIsLink(true);
//        colVidalMemberId.setHeaderLinkTitle("Sort by: Vidal Member Id.");
//        colVidalMemberId.setDBColumnName("tpa_enrollment_id");
//        addColumn(colVidalMemberId);   
                
        //Setting properties for Gn Member Id.
//        Column colGnMemberId=new Column("Gn Member Id");
//        colGnMemberId.setMethodName("getGnMemId");
//        colGnMemberId.setColumnWidth("10%");
//    //    colGnMemberId.setIsLink(true);
//        colGnMemberId.setHeaderLinkTitle("Sort by: Gn Member Id.");
//        colGnMemberId.setDBColumnName("global_net_member_id");
//        addColumn(colGnMemberId);     
        
        //Setting properties for Corp. Name
        Column colCorpName=new Column("Corp. Name");
        colCorpName.setMethodName("getGroupName");
        colCorpName.setColumnWidth("12%");
        colCorpName.setIsHeaderLink(true);
        colCorpName.setHeaderLinkTitle("Sort by: Corp. Name");
        colCorpName.setDBColumnName("GROUP_NAME");
        addColumn(colCorpName);
        
        //Setting properties for Member Name
        Column colMemberName=new Column("Member Name");
        colMemberName.setMethodName("getMemberName");
        colMemberName.setColumnWidth("12%");
        colMemberName.setIsHeaderLink(true);
        colMemberName.setHeaderLinkTitle("Sort by: Member Name");
        colMemberName.setDBColumnName("MEM_NAME");
        addColumn(colMemberName);
        
        Column colEmiratesId=new Column("Resident Card Number");
        colEmiratesId.setMethodName("getsEmiratesID");				
        colEmiratesId.setColumnWidth("12%");
        colEmiratesId.setIsHeaderLink(true);
        colEmiratesId.setHeaderLinkTitle("Sort by: Emirates Id");	
        colEmiratesId.setDBColumnName("emirate_id");				
        addColumn(colEmiratesId);
        
        
        //Setting properties for Agent Code
        Column colAgentCode=new Column("Agent Code");
        colAgentCode.setMethodName("getAgentCode");
        colAgentCode.setColumnWidth("5%");
        colAgentCode.setIsHeaderLink(true);
        colAgentCode.setHeaderLinkTitle("Sort by: Agent Code");
        colAgentCode.setDBColumnName("POLICY_AGENT_CODE");
        addColumn(colAgentCode);
        
        //Setting properties for Batch No.
        Column colBatchNo=new Column("Batch No.");
        colBatchNo.setMethodName("getBatchNbr");
        colBatchNo.setColumnWidth("9%");
        colBatchNo.setIsLink(true);
        colBatchNo.setLinkParamName("ThirdLink");
        colBatchNo.setIsHeaderLink(true);
        colBatchNo.setHeaderLinkTitle("Sort by: Batch No.");
        colBatchNo.setDBColumnName("BATCH_NUMBER");
        addColumn(colBatchNo);
        
        //Setting properties for Review
        Column colReview=new Column("Rev. Ct.");
        colReview.setMethodName("getReview");
        colReview.setIsHeaderLink(true);
        colReview.setHeaderLinkTitle("Sort by: Review Count");
        colReview.setColumnWidth("5%");
        colReview.setDBColumnName("REVIEW");
        addColumn(colReview);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        colSelect.setColumnWidth("1%");
        addColumn(colSelect);
        
    }//end of setTableProperties()
}//end of EnrollmentPolicyTable.java
