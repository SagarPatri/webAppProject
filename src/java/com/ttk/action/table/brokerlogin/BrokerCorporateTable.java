/**
 *   @ (#) BrokerCorporateTable.java Dec 29, 2015
 *   Project 	   : Dubai Project
 *   File          : BrokerCorporateTable.java
 *   Author        : Nagababu K
 *   Company       : RCS
 *   Date Created  : Dec 29, 2015
 *
 *   @author       :  Nagababu K
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 *
 */
package com.ttk.action.table.brokerlogin;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class BrokerCorporateTable extends Table
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        
        //Setting properties for Cashless No.
        Column colCorporateName=new Column("Corporate Name");
        colCorporateName.setMethodName("getCorporateName");
        colCorporateName.setColumnWidth("40%");
        colCorporateName.setIsLink(true);
        colCorporateName.setIsHeaderLink(true);
        colCorporateName.setHeaderLinkTitle("Sort by:Corporate Name");
        colCorporateName.setDBColumnName("GROUP_NAME");
        addColumn(colCorporateName);
        
        Column colPolicyNumber=new Column("Policy Number");
        colPolicyNumber.setMethodName("getPolicyNumber");
        colPolicyNumber.setColumnWidth("40%");
        colPolicyNumber.setIsHeaderLink(true);
        colPolicyNumber.setHeaderLinkTitle("Sort by:Policy Number");
        colPolicyNumber.setDBColumnName("POLICY_NUMBER");
        addColumn(colPolicyNumber);

        Column colPolicyYear=new Column("Policy Year");
        colPolicyYear.setMethodName("getPolicyDate");
        colPolicyYear.setColumnWidth("20%");
        colPolicyYear.setIsHeaderLink(true);
        colPolicyYear.setHeaderLinkTitle("Sort by:Policy Year");
        colPolicyYear.setDBColumnName("EFFECTIVE_TO_DATE");
        addColumn(colPolicyYear);   
/*
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);*/
    }// end of public void setTableProperties()

}// end of BrokerCorporateTable class
