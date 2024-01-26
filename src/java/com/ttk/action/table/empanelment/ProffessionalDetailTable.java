/**
 * @ (#) ProffessionalDetailTable.javaSep 24, 2005
 * Project      : TTK HealthCare Services
 * File         : ProffessionalDetailTable.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 06 Jan 2015
 *
 * @author       :  Kishor kumar S H
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
public class ProffessionalDetailTable extends Table 
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
        
      //Setting properties for contact type
        Column colContactType = new Column("License Number");
        colContactType.setMethodName("getLicenseNumb");
        colContactType.setColumnWidth("20%");
        colContactType.setIsHeaderLink(false);
        colContactType.setHeaderLinkTitle("Sort by License Number");
        colContactType.setDBColumnName("PROFESSIONAL_ID");
        addColumn(colContactType);
        

        //Setting properties for name
        Column colName = new Column("Doctor Name");
        colName.setMethodName("getDoctorName");
        colName.setColumnWidth("20%");
        colName.setIsLink(true);
        colName.setImageName("getImageName");
        colName.setImageTitle("getImageTitle");
        colName.setIsHeaderLink(false);
        colName.setLinkParamName("SecondLink");
        colName.setHeaderLinkTitle("Sort by Name");
        colName.setDBColumnName("CONTACT_NAME");
        addColumn(colName);
        
        

        //Setting properties for Designation
        Column colDesignationType = new Column("Authority Standard");
        colDesignationType.setMethodName("getAuthType");
        colDesignationType.setColumnWidth("20%");
        //colDesignationType.setIsLink(true);
        colDesignationType.setIsHeaderLink(false);
        colDesignationType.setHeaderLinkTitle("Sort by Authorized");
        colDesignationType.setDBColumnName("PRO_AUTHORITY");
        addColumn(colDesignationType);
        
        //Setting properties for Telephone
        Column colTelephone = new Column("Effective From");
        colTelephone.setMethodName("getEffectiveFrom");
        colTelephone.setColumnWidth("20%");
        //colTelephone.setIsLink(true);
        colTelephone.setIsHeaderLink(false);
        colTelephone.setHeaderLinkTitle("Sort by Effective From");
        colTelephone.setDBColumnName("START_DATE");
        addColumn(colTelephone);
        
        
        //Setting properties for Email
        Column colEmail = new Column("Effective To");
        colEmail.setMethodName("getEffectiveTo");
        colEmail.setColumnWidth("20%");
        //colEmail.setIsLink(true);
        colEmail.setIsHeaderLink(false);
        colEmail.setHeaderLinkTitle("Sort by Effective To");
        colEmail.setDBColumnName("END_DATE");
        addColumn(colEmail);
        
        
        //Setting properties for check box
        /*Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        //colSelect.setWidth(10);
        addColumn(colSelect);*/
        
    }//end of public void setTableProperties()
    
}
