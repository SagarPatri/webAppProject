/**
 * @ (#) InsuranceContactTable.javaNov 19, 2005
 * Project      : TTK HealthCare Services
 * File         : InsuranceContactTable.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Nov 19, 2005
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
 * this class provides the information of insurance contact table
 * 
 */
public class InsuranceContactTable extends Table
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
        //Setting properties for  contact name
        Column colName = new Column("Name");
        colName.setMethodName("getContactName");
        colName.setColumnWidth("15%");
        colName.setIsLink(true);
        colName.setImageName("getImageName");
        colName.setImageTitle("getImageTitle");
      //  colName.setIsHeaderLink(true);
       // colName.setHeaderLinkTitle("Sort by Name");
        colName.setDBColumnName("CONTACT_NAME");
        addColumn(colName);
        
        //Setting properties for contact designation
        Column colDesignation = new Column("Designation");
        colDesignation.setMethodName("getContactDesc");
        colDesignation.setColumnWidth("15%");
        //colDesignation.setIsHeaderLink(true);
        //colDesignation.setHeaderLinkTitle("Sort by Designation");
        colDesignation.setDBColumnName("DESIGNATION_DESCRIPTION");
        addColumn(colDesignation);
        
      //Setting properties for contact EMAIL
        Column colPrimaryEmailID  = new Column("Primary E-Mail ID");
        colPrimaryEmailID.setMethodName("getPrimaryEmailID");
        colPrimaryEmailID.setColumnWidth("25%");
       // colPrimaryEmailID.setIsHeaderLink(true);
        //colPrimaryEmailID.setHeaderLinkTitle("Sort by Email");
        colPrimaryEmailID.setDBColumnName("PRIMARY_EMAIL_ID");
        addColumn(colPrimaryEmailID);
        
      //Setting properties for contact Office PhoneNO
        Column colOfficePhoneNO  = new Column("Office Phone No");
        colOfficePhoneNO.setMethodName("getOfficePhoneNO");
        colOfficePhoneNO.setColumnWidth("15%");
       // colOfficePhoneNO.setIsHeaderLink(true);
        //colOfficePhoneNO.setHeaderLinkTitle("Sort by Phone");
        colOfficePhoneNO.setDBColumnName("OFF_PHONE_NO_1");
        addColumn(colOfficePhoneNO);
        
      //Setting properties for contact User_Role
        Column colUserRoleBRO  = new Column("User Role");
        colUserRoleBRO.setMethodName("getUserRoleBRO");
        colUserRoleBRO.setColumnWidth("15%");
       // colUserRoleBRO.setIsHeaderLink(true);
       // colUserRoleBRO.setHeaderLinkTitle("Sort by UserRoleBRO");
        colUserRoleBRO.setDBColumnName("ROLE_NAME");
        addColumn(colUserRoleBRO);
        
      //Setting properties for contact User_ID
        Column colUserID  = new Column("User ID");
        colUserID.setMethodName("getUserID");
        colUserID.setColumnWidth("10%");
       // colUserID.setIsHeaderLink(true);
        //colUserID.setHeaderLinkTitle("Sort by User_ID");
        colUserID.setDBColumnName("USER_ID");
        addColumn(colUserID);
        
      //Setting properties for contact Status
        Column colStatus  = new Column("Status");
        colStatus.setMethodName("getStatus");
        colStatus.setColumnWidth("15%");
       // colStatus.setIsHeaderLink(true);
        //colStatus.setHeaderLinkTitle("Sort by Status");
        colStatus.setDBColumnName("ACTIVE_YN");
        addColumn(colStatus);
        
        
        /*Column colAssociateDissAssociate  = new Column("Associated/Disassociated");
        colAssociateDissAssociate.setMethodName("getUserAssoDisAsso");
        colAssociateDissAssociate.setColumnWidth("10%");
        colAssociateDissAssociate.setIsHeaderLink(true);
        colAssociateDissAssociate.setHeaderLinkTitle("Sort by Associated/Disassociated");
        colAssociateDissAssociate.setDBColumnName("USER_ASSO_DISASSO");
        addColumn(colAssociateDissAssociate);*/
     
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
    }//end of setTableProperties()
}// end  of class InsuranceContactTable()
