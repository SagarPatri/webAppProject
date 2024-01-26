/**
 * @ (#) OnlinePreAuthHospitalTable.java
 * Project      : TTK HealthCare Services
 * File         : OnlinePreAuthHospitalTable.java
 * Author       : Balaji C R B
 * Company      : Span Systems Corporation
 * Date Created : March 13,2008
 *
 * @author       :Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *This class is used to build the table data for Hospital list in 
 *Online PreAuth
 *
 */
public class OnlinePreAuthHospitalTable extends Table{
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
        colHospitalName.setColumnWidth("30%");
        colHospitalName.setIsLink(true);
        colHospitalName.setIsHeaderLink(true);
        colHospitalName.setHeaderLinkTitle("Sort by: Hospital Name");
        colHospitalName.setDBColumnName("HOSP_NAME");
        addColumn(colHospitalName);

//      Setting properties for Address
        Column colAddress=new Column("Address");
        colAddress.setMethodName("getAddress");
        colAddress.setColumnWidth("40%");
        colAddress.setIsHeaderLink(true);
        colAddress.setHeaderLinkTitle("Sort by: Address");
        colAddress.setDBColumnName("ADDRESS");
        addColumn(colAddress);   

//      Setting properties for State
        Column colState=new Column("State");
        colState.setMethodName("getStateName");
        colState.setColumnWidth("15%");
        colState.setIsHeaderLink(true);
        colState.setHeaderLinkTitle("Sort by: State");
        colState.setDBColumnName("STATE_NAME");
        addColumn(colState);

//      Setting properties for City
        Column colCity=new Column("City");
        colCity.setMethodName("getCityDesc");
        colCity.setColumnWidth("15%");
        colCity.setIsHeaderLink(true);
        colCity.setHeaderLinkTitle("Sort by: City");
        colCity.setDBColumnName("CITY_DESCRIPTION");
        addColumn(colCity);
    }//end of setTableProperties()
}//end of OnlinePreAuthHospitalTable
