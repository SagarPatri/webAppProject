package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ConfiguredEmailListTable extends Table {

	public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
      /*Column colsno = new Column("S.NO");
        colsno.setMethodName("getSno");
        colsno.setColumnWidth("10%");
        colsno.setDBColumnName("INS_COMP_CODE_NUMBER");
        addColumn(colsno);*/
        //Setting properties for Company Code
        Column colRegulatoryAuthority = new Column("Regulatory Authority");
        colRegulatoryAuthority.setMethodName("getRegulatoryAuthority");
        colRegulatoryAuthority.setColumnWidth("10%");
       //colRegulatoryAuthority.setDBColumnName("DESCRIPTION");
        addColumn(colRegulatoryAuthority);
        
        Column colinsCompanyCode = new Column("Insurance Company Code");
        colinsCompanyCode.setMethodName("getInsuranceCode");
        colinsCompanyCode.setColumnWidth("10%");
       // colinsCompanyCode.setDBColumnName("DESCRIPTION");
        addColumn(colinsCompanyCode);
        
        //Setting properties for Insurance Company
        Column colInsCompany = new Column("Insurance Company name");
        colInsCompany.setMethodName("getInsuranceCompany");
        colInsCompany.setColumnWidth("20%");
       // colInsCompany.setDBColumnName("INS_COMP_NAME");
        addColumn(colInsCompany);
        
        Column colMailReciver = new Column("Mail Receiver Name");
        colMailReciver.setMethodName("getName");
        colMailReciver.setIsLink(true);
        colMailReciver.setLinkTitle("Edit Product");
        colMailReciver.setColumnWidth("20%");
      //  colMailReciver.setDBColumnName("INS_COMP_NAME");
        addColumn(colMailReciver);
        
        Column colEmailID = new Column("Primary Email Id");
        colEmailID.setMethodName("getPrimaryEmailId");
        colEmailID.setColumnWidth("20%");
       // colEmailID.setDBColumnName("INS_COMP_NAME");
        addColumn(colEmailID);
        
        Column colPhoneNumber = new Column("Phone Number");
        colPhoneNumber.setMethodName("getOfficePhone");
        colPhoneNumber.setColumnWidth("19%");
       // colPhoneNumber.setDBColumnName("INS_COMP_NAME");
        addColumn(colPhoneNumber);
        
        Column colImage = new Column("Delete");
        colImage.setIsImage(true);
        colImage.setIsImageLink(true);
        colImage.setColumnWidth("1");
        colImage.setLinkParamName("SecondLink");
        colImage.setImageName("getImageName");
        colImage.setImageTitle("getDeleteTitle");
        addColumn(colImage);
    }
}
