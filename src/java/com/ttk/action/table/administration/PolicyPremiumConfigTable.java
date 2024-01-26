package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class PolicyPremiumConfigTable extends Table {
	String flag;
	String SnoSize;
	String ProductNameSize;
	String MinAgeSize;
	String MaxAgeSize;
	String MaritalStatusSize;
	String GenderSize;
	String RelationSize;
	String GrossPremiumSize;
	String SalaryBand;
	String AuthorityProductId;
	String Auditlog;

	//String AspludTypeSize;
	
	
	public PolicyPremiumConfigTable(String flag)
	{
		this.flag=flag;
		if(this.flag.equals("ProductConfigTable"))
		{
			this.SnoSize="5";
			this.ProductNameSize="15";
			this.MinAgeSize="10";
			this.MaxAgeSize="10";
			this.MaritalStatusSize="15";
			this.GenderSize="14";
			this.RelationSize="20";
			this.GrossPremiumSize="10";
			
			//this.AspludTypeSize="10";
			
			
		}
		if(this.flag.equals("SchemeCapitation"))
		{

			
		/*	this.SnoSize="2%";
			this.ProductNameSize="12%";
			this.MinAgeSize="7%";
			this.MaxAgeSize="7%";
			this.MaritalStatusSize="3%";
			this.GenderSize="7%";
			this.RelationSize="10%";
			this.GrossPremiumSize="0%";
			this.SalaryBand="22%";
			this.AuthorityProductId="12%";
			this.Auditlog="8%";*/
			
			this.SnoSize="2%";
			this.ProductNameSize="15%";
			this.MinAgeSize="7%";
			this.MaxAgeSize="7%";
			this.MaritalStatusSize="5%";
			this.GenderSize="8%";
			this.RelationSize="10%";
			this.GrossPremiumSize="10%";
			this.SalaryBand="18%";
			this.AuthorityProductId="11%";
			this.Auditlog="7%";
			
			
		}
		if(this.flag.equals("SchemeNonCapitation"))
		{
			
			this.SnoSize="2%";
			this.ProductNameSize="15%";
			this.MinAgeSize="7%";
			this.MaxAgeSize="7%";
			this.MaritalStatusSize="5%";
			this.GenderSize="8%";
			this.RelationSize="10%";
			this.GrossPremiumSize="10%";
			this.SalaryBand="18%";
			this.AuthorityProductId="11%";
			this.Auditlog="7%";
		}
		
	
	}
	
	public void setTableProperties() {
		 setRowCount(20);
	        setCurrentPage(1);
	        setPageLinkCount(10);
	       
	        if(this.flag.equals("SchemeCapitation") || (this.flag.equals("SchemeNonCapitation")))
	        {  
	        Column colSNO = new Column("S.NO");
	        colSNO.setMethodName("getSno");
	        colSNO.setColumnWidth(this.SnoSize);
	        colSNO.setIsHeaderLink(true);
	        colSNO.setHeaderLinkTitle("Sort by: Product Name");
	        colSNO.setIsLink(true);
	        colSNO.setLinkTitle("Edit Product");
	        colSNO.setDBColumnName("PRODUCT_NAME");
	        addColumn(colSNO);
	        }
	       
	        if(this.flag.equals("SchemeCapitation") || (this.flag.equals("SchemeNonCapitation")))
	        {
	        Column colProductName =new Column("Product Name");
	        colProductName.setMethodName("getProductName");
	        colProductName.setColumnWidth(this.ProductNameSize);
	        colProductName.setIsHeaderLink(true);
	        colProductName.setHeaderLinkTitle("Sort by: Status");
	        colProductName.setDBColumnName("Product_Name");    
	        addColumn(colProductName);
	        
	        }
	        Column colminAge = new Column("Minimum Age");
	        colminAge.setMethodName("getMinAge");
	        colminAge.setColumnWidth(this.MinAgeSize);
	        colminAge.setIsHeaderLink(true);
	        colminAge.setHeaderLinkTitle("Sort by: Product Code");
	        colminAge.setDBColumnName("p_min_age");
	        addColumn(colminAge);
	        
	        //Setting properties for Insurance company name
	        Column colMaxAge = new Column("Maximum Age");
	        colMaxAge.setMethodName("getMaxAge");
	        colMaxAge.setColumnWidth(this.MaxAgeSize);
	        colMaxAge.setIsHeaderLink(true);
	        colMaxAge.setHeaderLinkTitle("Sort by: Insurance Company");
	        colMaxAge.setDBColumnName("p_max_age");
	        addColumn(colMaxAge);
	        
	        
	        //Setting properties for Status
	        Column colMaritalStatus =new Column("MaritalStatus");
	        colMaritalStatus.setMethodName("getMaritalStatus");
	        colMaritalStatus.setColumnWidth(this.MaritalStatusSize);
	        colMaritalStatus.setIsHeaderLink(true);
	        colMaritalStatus.setHeaderLinkTitle("Sort by: Status");
	        colMaritalStatus.setDBColumnName("p_marital_status");    
	        addColumn(colMaritalStatus);
	        
	        Column colGender =new Column("Gender Applicable");
	        colGender.setMethodName("getGender");
	        colGender.setColumnWidth(this.GenderSize);
	        colGender.setIsHeaderLink(true);
	        colGender.setHeaderLinkTitle("Sort by: Status");
	        colGender.setDBColumnName("p_gender");    
	        addColumn(colGender);
	        Column colRelation =new Column("Applicable To Relation");
	        colRelation.setMethodName("getRelation");
	        colRelation.setColumnWidth(this.RelationSize);
	        colRelation.setIsHeaderLink(true);
	        colRelation.setHeaderLinkTitle("Sort by: Status");
	        colRelation.setDBColumnName("p_gross_prem");    
	        addColumn(colRelation);
	        
	        Column colSalaryBand =new Column("Salary Band");
	        colSalaryBand.setMethodName("getSalaryBand");
	        colSalaryBand.setColumnWidth(this.SalaryBand);
	        colSalaryBand.setIsHeaderLink(true);
	        colSalaryBand.setHeaderLinkTitle("Sort by: Salary Band");
	        colSalaryBand.setDBColumnName("SALARY_BAND");    
	        addColumn(colSalaryBand);
	        
	        if(this.flag.equals("SchemeNonCapitation") || this.flag.equals("ProductConfigTable"))
	        {
	        Column colGrossPremium =new Column("Gross Premium");
	        colGrossPremium.setMethodName("getGrossPremium");
	        colGrossPremium.setColumnWidth(this.GrossPremiumSize);
	        colGrossPremium.setIsHeaderLink(true);
	        colGrossPremium.setHeaderLinkTitle("Sort by: Status");
	        colGrossPremium.setDBColumnName("p_relation");    
	        addColumn(colGrossPremium);
	        }  //setGrossPremium1
	        if(this.flag.equals("SchemeCapitation") )
	        {
	        Column colGrossPremium =new Column("Gross Premium");
	        colGrossPremium.setMethodName("getGrossPremium1");
	        colGrossPremium.setColumnWidth(this.GrossPremiumSize);
	        colGrossPremium.setIsHeaderLink(true);
	        colGrossPremium.setHeaderLinkTitle("Sort by: Status");
	        colGrossPremium.setDBColumnName("p_relation");    
	        addColumn(colGrossPremium);
	        }  //setGrossPremium1
	        
	        
	        
	        
	        //Setting properties for image  
	      
	        
	        if(this.flag.equals("SchemeCapitation") || (this.flag.equals("SchemeNonCapitation")))
	        {
	        Column colAuthorityProductId =new Column("Authority Product Id");
	        colAuthorityProductId.setMethodName("getAuthorityProductId");
	        colAuthorityProductId.setColumnWidth(this.AuthorityProductId);
	        colAuthorityProductId.setIsHeaderLink(true);
	        colAuthorityProductId.setHeaderLinkTitle("Sort by: Authority Product Id");
	        colAuthorityProductId.setDBColumnName("AUTH_PRODUCT_CODE");    
	        addColumn(colAuthorityProductId);
	        
	        Column colAuditlogs = new Column("Audit Log");
	        colAuditlogs.setMethodName("getAuditLog");
	        colAuditlogs.setColumnWidth(this.Auditlog);
	        colAuditlogs.setIsHeaderLink(true);
	        colAuditlogs.setHeaderLinkTitle("Sort by: Audit Log");
	        colAuditlogs.setIsLink(true);
	        colAuditlogs.setLinkTitle("View Audit Logs");
	        colAuditlogs.setLinkParamName("SecondLink");
	    //    colAuditlogs.setDBColumnName("AUTH_PRODUCT_CODE");    
	        addColumn(colAuditlogs);
	        
	        
	        
	        
	      
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

}
