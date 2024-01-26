/**
 * @ (#) PreAuthHospitalTable.java May 6, 2006
 * Project      : TTK HealthCare Services
 * File         : PreAuthHospitalTable.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : May 6, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *
 *
 */
public class PreAuthHospitalTable extends Table{
    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Hospital name
        Column colHospitalName = new Column("Hospital Name");
        colHospitalName.setMethodName("getHospitalName");
        colHospitalName.setColumnWidth("28%");
        colHospitalName.setIsLink(true);
        colHospitalName.setIsHeaderLink(true);
        colHospitalName.setHeaderLinkTitle("Sort by: Hospital Name");
        colHospitalName.setDBColumnName("HOSP_NAME");
        addColumn(colHospitalName);


        //Setting properties Empanelment No.
        Column colEmpanelmentNo = new Column("Empanelment No.");
        colEmpanelmentNo.setMethodName("getEmplNumber");
        colEmpanelmentNo.setColumnWidth("17%");
        colEmpanelmentNo.setIsHeaderLink(true);
        colEmpanelmentNo.setHeaderLinkTitle("Sort by: Empanelment No");
        colEmpanelmentNo.setDBColumnName("EMPANEL_NUMBER");
        addColumn(colEmpanelmentNo);

        //Setting properties for  Hospital Status.
        Column colHospitalStatus=new Column("Status");
        colHospitalStatus.setMethodName("getHospStatus");
        colHospitalStatus.setColumnWidth("15%");
        colHospitalStatus.setIsHeaderLink(true);
        colHospitalStatus.setHeaderLinkTitle("Sort by: Status");
        colHospitalStatus.setDBColumnName("HOSP_STATUS");
        addColumn(colHospitalStatus);

//      Setting properties for Address
        Column colAddress=new Column("Address1");
        colAddress.setMethodName("getAddress1");
        colAddress.setColumnWidth("20%");
        colAddress.setIsHeaderLink(true);
        colAddress.setHeaderLinkTitle("Sort by: Address");
        colAddress.setDBColumnName("ADDRESS_1");
        addColumn(colAddress);

		Column colAddress2=new Column("Address2");
        colAddress2.setMethodName("getAddress2");
        colAddress2.setColumnWidth("20%");
        colAddress2.setIsHeaderLink(true);
        colAddress2.setHeaderLinkTitle("Sort by: Address");
        colAddress2.setDBColumnName("ADDRESS_2");
        addColumn(colAddress2);
		
		Column colAddress3=new Column("Address3");
        colAddress3.setMethodName("getAddress3");
        colAddress3.setColumnWidth("20%");
        colAddress3.setIsHeaderLink(true);
        colAddress3.setHeaderLinkTitle("Sort by: Address");
        colAddress3.setDBColumnName("ADDRESS_3");
        addColumn(colAddress3);
		
		
		
		
		
//      Setting properties for State
        Column colState=new Column("State");
        colState.setMethodName("getStateName");
        colState.setColumnWidth("10%");
        colState.setIsHeaderLink(true);
        colState.setHeaderLinkTitle("Sort by: State");
        colState.setDBColumnName("STATE_NAME");
        addColumn(colState);

//      Setting properties for City
        Column colCity=new Column("City");
        colCity.setMethodName("getCityDesc");
        colCity.setColumnWidth("10%");
        colCity.setIsHeaderLink(true);
        colCity.setHeaderLinkTitle("Sort by: City");
        colCity.setDBColumnName("CITY_DESCRIPTION");
        addColumn(colCity);
    }//end of setTableProperties()
}//end of PreAuthHospitalTable.java
