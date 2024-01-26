package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class MemberListTable extends Table{

	@Override
	public void setTableProperties() {
		// TODO Auto-generated method stub
		
		
		
		setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        Column colMemberId = new Column("Member Id");
        colMemberId.setMethodName("getMemberId");
        colMemberId.setColumnWidth("14%");
        colMemberId.setIsLink(true);
        colMemberId.setDBColumnName("tpa_enrollment_id");
        addColumn(colMemberId);
        
   /*     Column dhpoMemberId = new Column("Member Id");
        dhpoMemberId.setMethodName("getDhpoMemberId");
        dhpoMemberId.setColumnWidth("14%");
        dhpoMemberId.setDBColumnName("DHPO_MEMBER_ID");
        addColumn(dhpoMemberId);  */

        
        Column colMemberName = new Column("Member Name");
        colMemberName.setMethodName("getPatientName");
        colMemberName.setColumnWidth("10%");
        colMemberName.setIsLink(true);
        colMemberName.setDBColumnName("MEM_NAME");
        addColumn(colMemberName);
        
        
        
        Column colEmiratesId = new Column("Civil Id");
        colEmiratesId.setMethodName("getEmirateId");
        colEmiratesId.setColumnWidth("10%");
       // colEmiratesId.setIsLink(true);
        colEmiratesId.setDBColumnName("EMIRATE_ID");
        addColumn(colEmiratesId);
        
        
        
        Column colPolicyNo = new Column("Policy No");
        colPolicyNo.setMethodName("getPolicyNumber");
        colPolicyNo.setColumnWidth("10%");
        colPolicyNo.setDBColumnName("policy_number");
        addColumn(colPolicyNo);
        
        
        Column colMembetNetworType = new Column("Member's Network Type");
        colMembetNetworType.setMethodName("getMember_network");
        colMembetNetworType.setColumnWidth("10%");
        colMembetNetworType.setDBColumnName("member_network");
        addColumn(colMembetNetworType);
        
        
        Column colPayerId = new Column("Payer ID");
        colPayerId.setMethodName("getPayerId");
        colPayerId.setColumnWidth("8%");
        colPayerId.setDBColumnName("PAYER_ID");
        addColumn(colPayerId);
        
        
        
        Column colCorporateGroupId = new Column("Corporate GroupID");
        colCorporateGroupId.setMethodName("getCorporate_id");
        colCorporateGroupId.setColumnWidth("10%");
        colCorporateGroupId.setDBColumnName("corporate_id");
        addColumn(colCorporateGroupId);
        
        
        
        Column colCorporateName = new Column("Corporate Name");
        colCorporateName.setMethodName("getCorporateName");
        colCorporateName.setColumnWidth("10%");
        colCorporateName.setDBColumnName("CORPORATE_NAME");
        addColumn(colCorporateName);
        
        
        
        Column colPolicySDate = new Column("Member Valid From Date");
    //    Column colPolicySDate = new Column("Policy Start Date");
        colPolicySDate.setMethodName("getStart_date");
        colPolicySDate.setColumnWidth("9%");
        colPolicySDate.setDBColumnName("start_date");
        addColumn(colPolicySDate);
        
        Column colDiagnosisType = new Column("Member Expiry Date"); 
    //    Column colDiagnosisType = new Column("Policy End Date");
        colDiagnosisType.setMethodName("getEnd_date");
        colDiagnosisType.setColumnWidth("9%");
        colDiagnosisType.setDBColumnName("end_date");
        addColumn(colDiagnosisType);
		
		
		
	}
	
	
	
	

}
