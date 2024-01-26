package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class AuditLogsTable extends Table{

	public void setTableProperties() {
		
		
		    setRowCount(20);
	        setCurrentPage(1);
	        setPageLinkCount(20);
	        
	        
	      
	        
	        
	        //Setting properties for Company Code
	        Column colOffice = new Column("Field Name");
	        colOffice.setMethodName("getFieldname");
	        colOffice.setColumnWidth("20%");
	       // colOffice.setIsLink(true);
	       // colOffice.setIsHeaderLink(true);
	        //colOffice.setHeaderLinkTitle("Sort by: Company Code");
	        colOffice.setDBColumnName("modified_fields");
	        addColumn(colOffice);
	        
	        //Setting properties for Insurance Company
	        Column colInsCompany = new Column("Modified value");
	        colInsCompany.setMethodName("getValueBeforeModification");
	        colInsCompany.setColumnWidth("30%");
	       // colInsCompany.setIsHeaderLink(true);
	        //colInsCompany.setHeaderLinkTitle("Sort by: Insurance Company");
	       colInsCompany.setDBColumnName("actual_value");
	        addColumn(colInsCompany);
	        
	        
	     /*   Column colRegulatoryAuthority = new Column("Value after modification");
	        colRegulatoryAuthority.setMethodName("getValueAfterModification");
	        colRegulatoryAuthority.setColumnWidth("15%");
	       // colInsCompany.setIsHeaderLink(true);
	        //colInsCompany.setHeaderLinkTitle("Sort by: Insurance Company");
	       // colRegulatoryAuthority.setDBColumnName("INS_COMP_NAME");
	        addColumn(colRegulatoryAuthority);*/
	        
	        //Setting properties for Office Type
	        Column colOffType = new Column("Date of modification");
	        colOffType.setMethodName("getDateOfModification");
	        colOffType.setColumnWidth("15%");
	        //colOffType.setIsHeaderLink(true);
	       // colOffType.setHeaderLinkTitle("Sort by: Office Type");
	        colOffType.setDBColumnName("added_date");
	        addColumn(colOffType);
	        
	        //Setting properties for user Vidal Health TPA Branch
	        Column colUserType = new Column("Cust.Endorsement No");
	        colUserType.setMethodName("getCustEndorsementNo");
	        colUserType.setColumnWidth("15%");
	        //colUserType.setIsHeaderLink(true);
	        //colUserType.setHeaderLinkTitle("Sort by: Vidal Health Branch");
	        colUserType.setDBColumnName("cust_endorsement_number");
	        addColumn(colUserType);
		
		
	        Column colUserName = new Column("User Name");
	        colUserName.setMethodName("getModifiedUserName");
	        colUserName.setColumnWidth("10%");
	        //colUserType.setIsHeaderLink(true);
	        //colUserType.setHeaderLinkTitle("Sort by: Vidal Health Branch");
	        colUserType.setDBColumnName("user_id");
	        addColumn(colUserName);
	        
	        
	        
	        Column colUserRole = new Column("User Role");
	        colUserRole.setMethodName("getModifiedUserRole");
	        colUserRole.setColumnWidth("10%");
	        //colUserType.setIsHeaderLink(true);
	        //colUserType.setHeaderLinkTitle("Sort by: Vidal Health Branch");
	        colUserType.setDBColumnName("role_name");
	        addColumn(colUserRole);
		
		
	}

}
