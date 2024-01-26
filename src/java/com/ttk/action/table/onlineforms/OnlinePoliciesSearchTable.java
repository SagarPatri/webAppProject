/**
 * @ (#) OnlinePoliciesTable.java July 24, 2006
 * Project       : TTK HealthCare Services
 * File          : OnlinePoliciesTable.java
 * Author        : Harsha Vardhan B N
 * Company       : Span Systems Corporation
 * Date Created  : July 24, 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.onlineforms;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class OnlinePoliciesSearchTable extends Table {
    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */

        public void setTableProperties()
        {
            setRowCount(10);
            setCurrentPage(1);
            setPageLinkCount(10);

            //Setting properties for Scheme No.
            Column colPolicyNo = new Column("Scheme No.");
            colPolicyNo.setMethodName("getPolicyNbr");
            colPolicyNo.setColumnWidth("40%");
            colPolicyNo.setIsLink(true);
            colPolicyNo.setLinkTitle("Edit Scheme No");
            colPolicyNo.setDBColumnName("POLICY_NUMBER");
            addColumn(colPolicyNo);
            
            //Setting properties for Group Name
            Column colGroupName=new Column("Group/Company Name");
            colGroupName.setMethodName("getGroupName");
            colGroupName.setColumnWidth("20%");
            colGroupName.setHeaderLinkTitle("Sort by: Group Name");
            colGroupName.setDBColumnName("GROUP_NAME");
            addColumn(colGroupName);
            
            //Setting properties for Policy Start Date
            Column colStartDate = new Column("Scheme Start Date");
            colStartDate.setMethodName("getEffectiveFromDate");
            colStartDate.setColumnWidth("20%");
            colStartDate.setDBColumnName("EFFECTIVE_FROM_DATE");
            addColumn(colStartDate);

            //Setting properties for Policy End Date
            Column colEndDate = new Column("Scheme End Date");
            colEndDate.setMethodName("getEffectiveEndDate");
            colEndDate.setColumnWidth("20%");
            colEndDate.setDBColumnName("EFFECTIVE_TO_DATE");
            addColumn(colEndDate);
            
          //Setting properties for Location
            Column colLocation = new Column("Location");
            colLocation.setMethodName("getLocation");
            colLocation.setColumnWidth("20%");
            colLocation.setDBColumnName("OFFICE_NAME");
            addColumn(colLocation);
            
            //Setting properties for image
            /*Column colImage = new Column("");
            colImage.setIsImage(true);
            colImage.setIsImageLink(true);
            colImage.setImageName("getSummaryImageName");
            colImage.setImageTitle("getSummaryImageTitle");
            addColumn(colImage);*/

        }//end of public void setTableProperties()
    }//end of class OnlinePoliciesTable
