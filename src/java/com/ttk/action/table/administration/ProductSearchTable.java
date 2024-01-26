/**
 * @ (#) ProductSearchTable.java Nov 14, 2005
 * Project      : TTK HealthCare Services
 * File         : ProductSearchTable.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Nov 14, 2005
 *
 * @author       :  Arun K N
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
public class ProductSearchTable extends Table{
    
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //Setting properties for Product Name
        Column colProductName = new Column("Product Name");
        colProductName.setMethodName("getProductName");
        colProductName.setColumnWidth("20%");
        colProductName.setIsHeaderLink(true);
        colProductName.setHeaderLinkTitle("Sort by: Product Name");
        colProductName.setIsLink(true);
        colProductName.setLinkTitle("Edit Product");
        colProductName.setDBColumnName("PRODUCT_NAME");
        addColumn(colProductName);
        
        //Setting properties for Product Code
        Column colProductCode = new Column("Product Code");
        colProductCode.setMethodName("getProductCode");
        colProductCode.setColumnWidth("15%");
        colProductCode.setIsHeaderLink(true);
        colProductCode.setHeaderLinkTitle("Sort by: Product Code");
        colProductCode.setDBColumnName("INS_PRODUCT_CODE");
        addColumn(colProductCode);
        
        //Setting properties for Product Authority
        Column colProductAuth = new Column("Product Authority");
        colProductAuth.setMethodName("getProductAuthority");
        colProductAuth.setColumnWidth("20%");
        colProductAuth.setIsHeaderLink(true);
        colProductAuth.setHeaderLinkTitle("Sort by: Product Authority");
        colProductAuth.setDBColumnName("AUTHORITY_TYPE");    
        addColumn(colProductAuth);
        
        //Setting properties for Insurance company name
        Column colInsCompanyName = new Column("Insurance Company");
        colInsCompanyName.setMethodName("getCompanyName");
        colInsCompanyName.setColumnWidth("20%");
        colInsCompanyName.setIsHeaderLink(true);
        colInsCompanyName.setHeaderLinkTitle("Sort by: Insurance Company");
        colInsCompanyName.setDBColumnName("INS_COMP_NAME");
        addColumn(colInsCompanyName);
        
        //Setting properties for Insurance company ID
        Column colInsInsuranceCompanyID = new Column("Insurance Company ID");
        colInsInsuranceCompanyID.setMethodName("getInsuranceCompanyID");
        colInsInsuranceCompanyID.setColumnWidth("25%");
        colInsInsuranceCompanyID.setIsHeaderLink(true);
        colInsInsuranceCompanyID.setHeaderLinkTitle("Sort by:Insurance CompanyID");
        colInsInsuranceCompanyID.setDBColumnName("INS_COMP_CODE_NUMBER ");
        addColumn(colInsInsuranceCompanyID);
        
        //Setting properties for Status
        Column colTtkBranch =new Column("Status");
        colTtkBranch.setMethodName("getStatus");
        colTtkBranch.setColumnWidth("25%");
        colTtkBranch.setIsHeaderLink(true);
        colTtkBranch.setHeaderLinkTitle("Sort by: Status");
        colTtkBranch.setDBColumnName("TPA_GENERAL_CODE.DESCRIPTION");    
        addColumn(colTtkBranch);
        
        //Setting properties for image  
        Column colImage2 = new Column("");
        colImage2.setIsImage(true);
        colImage2.setIsImageLink(true);
        colImage2.setImageName("getSynchronizeImageName");
        colImage2.setImageTitle("getSynchronizeImageTitle");
        addColumn(colImage2);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");       
        addColumn(colSelect); 
    }//end of setTableProperties()

}//end of ProductSearchTable
