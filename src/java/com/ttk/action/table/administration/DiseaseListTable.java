/**
 * @ (#)DiseaseListTable.java 23rd Sep 2010
 * Project      : TTK HealthCare Services
 * File         : DiseaseListTable.java
 * Author       : Manikanta Kumar G G
 * Company      : Span Systems Corporation
 * Date Created : 23rd Sep 2010
 *
 * @author       :  Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class DiseaseListTable extends Table 
{
	
	 public void setTableProperties()
	    {
	    	setRowCount(100);
	        setCurrentPage(1);
	        setPageLinkCount(10);

	        //Setting properties for Disease Name
	        Column colDiseaseName=new Column("Disease Code");
	        colDiseaseName.setMethodName("getDiseaseCode");
	        colDiseaseName.setColumnWidth("25%");
	        //colDiseaseName.setIsLink(true);
	        //colDiseaseName.setIsHeaderLink(true);
	        //colDiseaseName.setHeaderLinkTitle(" Sort by: Disease Code");
	        //colDiseaseName.setLinkTitle("Edit Disease Name");
	        colDiseaseName.setDBColumnName("TPA_INS_SPECIFIC_CODE.CODE");
	        addColumn(colDiseaseName);
	        
	        //Setting properties for Disease Description
	        Column colDiseaseDesc = new Column("Disease Description");
	        colDiseaseDesc.setMethodName("getDiseaseDesc");
	        colDiseaseDesc.setColumnWidth("70%");
	        //colDiseaseDesc.setIsHeaderLink(true);
	       // colDiseaseDesc.setHeaderLinkTitle(" Sort by: Disease Description");
	        //colDiseaseName.setLinkTitle("Edit Disease Description");
	        colDiseaseDesc.setDBColumnName("TPA_INS_SPECIFIC_CODE.DESCRIPTION");
	        addColumn(colDiseaseDesc);
	        
	      //Setting properties for check box
	        Column colSelect = new Column("Select");
	        colSelect.setComponentType("checkbox");
	        colSelect.setComponentName("chkopt");
	        addColumn(colSelect);
	        
	    }//end of setTableProperties()

}//end of DiseaseListTable()
