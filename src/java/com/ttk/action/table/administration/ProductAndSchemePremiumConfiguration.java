package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;
import com.ttk.common.TTKCommon;

public class ProductAndSchemePremiumConfiguration extends Table {

	public void setTableProperties() {
		 setRowCount(20);
	        setCurrentPage(1);
	        setPageLinkCount(10);
	       
	        //Setting properties for Product Name
	        Column colSNO = new Column("S.NO");
	        colSNO.setMethodName("getSno");
	        colSNO.setColumnWidth("5%");
	        colSNO.setIsHeaderLink(true);
	        colSNO.setHeaderLinkTitle("Sort by: Product Name");
	        colSNO.setIsLink(true);
	        colSNO.setLinkTitle("Edit Product");
	        colSNO.setDBColumnName("PRODUCT_NAME");
	        addColumn(colSNO);
	        
	        //Setting properties for Product Code
	        Column colminAge = new Column("Minimum Age");
	        colminAge.setMethodName("getMinAge");
	        colminAge.setColumnWidth("10%");
	        colminAge.setIsHeaderLink(true);
	        colminAge.setHeaderLinkTitle("Sort by: Product Code");
	        colminAge.setDBColumnName("p_min_age");
	        addColumn(colminAge);
	        
	        //Setting properties for Insurance company name
	        Column colMaxAge = new Column("Maximum age");
	        colMaxAge.setMethodName("getMaxAge");
	        colMaxAge.setColumnWidth("10%");
	        colMaxAge.setIsHeaderLink(true);
	        colMaxAge.setHeaderLinkTitle("Sort by: Insurance Company");
	        colMaxAge.setDBColumnName("p_max_age");
	        addColumn(colMaxAge);
	        
	        
	        //Setting properties for Status
	        Column colMaritalStatus =new Column("MaritalStatus");
	        colMaritalStatus.setMethodName("getMaritalStatus");
	        colMaritalStatus.setColumnWidth("15%");
	        colMaritalStatus.setIsHeaderLink(true);
	        colMaritalStatus.setHeaderLinkTitle("Sort by: Status");
	        colMaritalStatus.setDBColumnName("p_marital_status");    
	        addColumn(colMaritalStatus);
	        
	        Column colGender =new Column("Gender Applicable");
	        colGender.setMethodName("getGender");
	        colGender.setColumnWidth("20%");
	        colGender.setIsHeaderLink(true);
	        colGender.setHeaderLinkTitle("Sort by: Status");
	        colGender.setDBColumnName("p_gender");    
	        addColumn(colGender);
	        Column colRelation =new Column("Applicable To Relation");
	        colRelation.setMethodName("getRelation");
	        colRelation.setColumnWidth("20%");
	        colRelation.setIsHeaderLink(true);
	        colRelation.setHeaderLinkTitle("Sort by: Status");
	        colRelation.setDBColumnName("p_gross_prem");    
	        addColumn(colRelation);
	        Column colGrossPremium =new Column("Gross Premium");
	        colGrossPremium.setMethodName("getGrossPremium");
	        colGrossPremium.setColumnWidth("20%");
	        colGrossPremium.setIsHeaderLink(true);
	        colGrossPremium.setHeaderLinkTitle("Sort by: Status");
	        colGrossPremium.setDBColumnName("p_relation");    
	        addColumn(colGrossPremium);
	        //Setting properties for image  
	        Column colImage = new Column("Delete");
	        colImage.setIsImage(true);
	        colImage.setIsImageLink(true);
	        colImage.setLinkParamName("SecondLink");
	        colImage.setImageName("getImageName");
	        colImage.setImageTitle("getDeleteTitle");
	        addColumn(colImage);
	       /* Column colImage2 = new Column("");
	        colImage2.setIsImage(true);
	        colImage2.setIsImageLink(true);
	        colImage2.setImageName("getSynchronizeImageName");
	        colImage2.setImageTitle("getSynchronizeImageTitle");
	        addColumn(colImage2);*/
	        
	        //Setting properties for check box
	      /*  Column colSelect = new Column("Select");
	        colSelect.setComponentType("checkbox");
	        colSelect.setComponentName("chkopt");       
	        addColumn(colSelect); */
		
	}

}
