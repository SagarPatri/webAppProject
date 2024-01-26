package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class DHPOLogTable extends Table {
	 public void setTableProperties()
	    {
	        setRowCount(10);
	        setCurrentPage(1);
	        setPageLinkCount(10);
	        Column colsno = new Column("S.NO");
	        colsno.setMethodName("getSno");
	        colsno.setColumnWidth("5%");
	        colsno.setIsLink(true);
	      /*  colsno.setIsHeaderLink(true);*/
	        //colOffice.setHeaderLinkTitle("Sort by: Company Code");
	       // colsno.setDBColumnName("INS_COMP_CODE_NUMBER");
	        addColumn(colsno);
	        //Setting properties for Company Code
	        Column colOffice = new Column("File Name");
	        colOffice.setMethodName("getFileName");
	        colOffice.setColumnWidth("20%");
	        colOffice.setIsLink(true);
	       // colOffice.setIsHeaderLink(true);
	        //colOffice.setHeaderLinkTitle("Sort by: Company Code");
	       // colOffice.setDBColumnName("INS_COMP_CODE_NUMBER");
	        addColumn(colOffice);
	        
	        //Setting properties for Insurance Company
	        Column colInsCompany = new Column("Insurance Company");
	        colInsCompany.setMethodName("getInsurenceCompany");
	        colInsCompany.setColumnWidth("25%");
	       // colInsCompany.setIsHeaderLink(true);
	        //colInsCompany.setHeaderLinkTitle("Sort by: Insurance Company");
	       // colInsCompany.setDBColumnName("INS_COMP_NAME");
	        addColumn(colInsCompany);
	        
	        
	        Column colRegulatoryAuthority = new Column("Regulatory Authority");
	        colRegulatoryAuthority.setMethodName("getRegulatoryAuthority");
	        colRegulatoryAuthority.setColumnWidth("15%");
	       // colInsCompany.setIsHeaderLink(true);
	        //colInsCompany.setHeaderLinkTitle("Sort by: Insurance Company");
	       // colRegulatoryAuthority.setDBColumnName("INS_COMP_NAME");
	        addColumn(colRegulatoryAuthority);
	        
	        //Setting properties for Office Type
	        Column colOffType = new Column("Insurance Company code");
	        colOffType.setMethodName("getInsurenceCompanyCode");
	        colOffType.setColumnWidth("15%");
	        //colOffType.setIsHeaderLink(true);
	       // colOffType.setHeaderLinkTitle("Sort by: Office Type");
	      //  colOffType.setDBColumnName("DESCRIPTION");
	        addColumn(colOffType);
	        
	        //Setting properties for user Vidal Health TPA Branch
	        Column colUserType = new Column("Uploaded Date");
	        colUserType.setMethodName("getUploadedDate");
	        colUserType.setColumnWidth("20%");
	        //colUserType.setIsHeaderLink(true);
	        //colUserType.setHeaderLinkTitle("Sort by: Vidal Health Branch");
	        //colUserType.setDBColumnName("getUploadedDate");
	        addColumn(colUserType);

	    }
}
