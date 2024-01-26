package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class OldLicenseListTable extends Table {

	@Override
	public void setTableProperties() {
		
		
		

		
		setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        Column colMemberId = new Column("License Code");
        colMemberId.setMethodName("getIrdaNumber");
        colMemberId.setColumnWidth("30%");
        colMemberId.setDBColumnName("hosp_licenc_numb");
        addColumn(colMemberId);

        
        Column colMemberName = new Column("Authority");
        colMemberName.setMethodName("getRegAuthority");
        colMemberName.setColumnWidth("20%");
        
        colMemberName.setDBColumnName("REGIST_AUTHORITY");
        addColumn(colMemberName);
        
        
        
        Column colEmiratesId = new Column("Effective Date");
        colEmiratesId.setMethodName("getsStartDate");
        colEmiratesId.setColumnWidth("20%");
       // colEmiratesId.setIsLink(true);
        colEmiratesId.setDBColumnName("LICENC_EFFECTIVE_FROM");
        addColumn(colEmiratesId);
        
        
        
        Column colPolicyNo = new Column("Expiry Date");
        colPolicyNo.setMethodName("getsEndDate");
        colPolicyNo.setColumnWidth("20%");
        colPolicyNo.setDBColumnName("LICENC_EFFECTIVE_TO");
        addColumn(colPolicyNo);
        
        
        
       /* Column colImage = new Column("Delete");
        colImage.setIsImage(true);
       colImage.setIsImageLink(true);
        colImage.setColumnWidth("1");
        colImage.setLinkParamName("SecondLink");
        colImage.setImageName("getImageName");
        colImage.setImageTitle("getDeleteTitle");
        addColumn(colImage);*/
        
        
        
		
	}

}
