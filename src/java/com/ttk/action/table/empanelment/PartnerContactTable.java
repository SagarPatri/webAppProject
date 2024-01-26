/**
 * @ (#) HospitalContactTable.javaSep 24, 2005
 * Project      : TTK HealthCare Services
 * File         : HospitalContactTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Sep 24, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.empanelment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * 
 * this class provides the information of hospital contact table
 * 
 */
public class PartnerContactTable extends Table 
{
    /**
     * This creates the columnproperties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(20);

        //Setting properties for name
        Column colName = new Column("Name");
        colName.setMethodName("getContactName");
        colName.setColumnWidth("20%");
        colName.setIsLink(true);
        colName.setImageName("getImageName");
        colName.setImageTitle("getImageTitle");
       // colName.setIsHeaderLink(true);
       // colName.setHeaderLinkTitle("Sort by Name");
        colName.setDBColumnName("CONTACT_NAME");
        addColumn(colName);
        
        

        //Setting properties for Designation
        Column colDesignationType = new Column("Designation");
        colDesignationType.setMethodName("getDesignation");
        colDesignationType.setColumnWidth("20%");
        //colDesignationType.setIsLink(true);
       // colDesignationType.setIsHeaderLink(true);
       // colDesignationType.setHeaderLinkTitle("Sort by Designation");
        colDesignationType.setDBColumnName("DESIGNATION");
        addColumn(colDesignationType);
        
        //Setting properties for Telephone
        Column colTelephone = new Column("Telephone");
        colTelephone.setMethodName("getPhone");
        colTelephone.setColumnWidth("20%");
        //colTelephone.setIsLink(true);
        //colTelephone.setIsHeaderLink(true);
       // colTelephone.setHeaderLinkTitle("Sort by Telephone");
        colTelephone.setDBColumnName("MOBILE_NO");
        addColumn(colTelephone);
        
        
        //Setting properties for Email
        Column colEmail = new Column("Email");
        colEmail.setMethodName("getEmail");
        colEmail.setColumnWidth("20%");
        //colEmail.setIsLink(true);
        //colEmail.setIsHeaderLink(true);
        //colEmail.setHeaderLinkTitle("Sort by Email");
        colEmail.setDBColumnName("EMAIL");
        addColumn(colEmail);
        
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);
        
    }//end of public void setTableProperties()
    
}
