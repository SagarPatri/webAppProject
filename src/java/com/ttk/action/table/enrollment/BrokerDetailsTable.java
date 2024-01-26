
/**
 * @ (#) UploadMOUDocsAction.java 31 Dec 2014
 * Project      : TTK HealthCare Services
 * File         :UploadMOUDocsAction.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 31 Dec 2014
 *
 * @author       : Kishor kumar S H
 * Modified by   : 
 * Modified date : 31 Dec 2014
 */
package com.ttk.action.table.enrollment;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class BrokerDetailsTable extends Table{
	/**
     * This creates the column properties objects for each and 
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
    	setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        //Setting properties for Link Broker Code
        //Link for Broker Code colBrokerCode.setIsLink(true);
       
        Column colBrokerCode =new Column("Broker Code");
        colBrokerCode.setMethodName("getCompanyCodeNbr");
        colBrokerCode.setColumnWidth("25%");
  //      colBrokerCode.setIsLink(true);
        colBrokerCode.setHeaderLinkTitle("Sort by: Broker Code");
        colBrokerCode.setDBColumnName("INS_COMP_CODE_NUMBER");
        addColumn(colBrokerCode);
        
        //Setting properties for Broker Name
        Column colBrokerName = new Column("Broker Name");
        colBrokerName.setMethodName("getCompanyName");
        colBrokerName.setColumnWidth("25%");
        colBrokerName.setHeaderLinkTitle("Sort by: Broker Name");
        colBrokerName.setDBColumnName("INS_COMP_NAME");
        addColumn(colBrokerName);
        
      //Setting properties for Broker Authority
        Column colBrokerAuthority = new Column("Broker Authority");
        colBrokerAuthority.setMethodName("getRegAuthority");
        colBrokerAuthority.setColumnWidth("25%");
        colBrokerAuthority.setHeaderLinkTitle("Sort by: Broker Authority");
        colBrokerAuthority.setDBColumnName("BROKER_AUTHORITY");
        addColumn(colBrokerAuthority);
        
        //Setting properties for Vidal Health Branch
        Column colBranch = new Column("Vidal Health Branch");
        colBranch.setMethodName("getOfficeInfo");
        colBranch.setColumnWidth("25%");
        colBranch.setHeaderLinkTitle("Sort by: Vidal Health Branch");
        colBranch.setDBColumnName("OFFICE_NAME");
        addColumn(colBranch);
        
       // Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);       
    }//end of setTableProperties()
}//end of MouUploadFilesTable
