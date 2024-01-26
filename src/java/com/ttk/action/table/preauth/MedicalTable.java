
/**
 * @ (#) MedicalTable.java 8th May 2006
 * Project      : TTK HealthCare Services
 * File         : InvestigationTable.java
 * Author       : Pradeep R
 * Company      : Span Systems Corporation
 * Date Created : 8th May 2006
 *
 * @author       : Pradeep R
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class MedicalTable extends Table {

     /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
          setRowCount(50);
            setCurrentPage(1);
            setPageLinkCount(10);

            //Setting properties for Ailment
            Column colAilment = new Column("Ailment");
            colAilment.setMethodName("getDescription");
            colAilment.setColumnWidth("24%");
            colAilment.setIsLink(true);
            colAilment.setDBColumnName("AILMENT_DESC");
            addColumn(colAilment);

            //Setting properties for Treatment Plan
            Column colTreatmentPlan = new Column("Treatment Plan");
            colTreatmentPlan.setMethodName("getTreatmentPlanDesc");
            colTreatmentPlan.setColumnWidth("15%");
            colTreatmentPlan.setDBColumnName("TREATMENT_DESC");
            addColumn(colTreatmentPlan);

            //Setting properties for ICD Code
            Column colICDCode = new Column("ICD Code");
            colICDCode.setMethodName("getICDCode");
            colICDCode.setColumnWidth("11%");
            colICDCode.setDBColumnName("ICD_CODE");
            addColumn(colICDCode);

            //Setting properties for Procedures
            Column colProcedures = new Column("Procedures");
            colProcedures.setMethodName("getProcCodes");
            colProcedures.setColumnWidth("20%");
            colProcedures.setDBColumnName("PROC_CODE");
            addColumn(colProcedures);
            
            //Setting properties for Package
            Column colPackages = new Column("Package");
            colPackages.setMethodName("getPackageDescription");
            colPackages.setColumnWidth("26%");
            colPackages.setDBColumnName("DESCRIPTION");
            addColumn(colPackages);

            //Setting properties for check box
            Column colSelect = new Column("Select");
            colSelect.setComponentType("checkbox");
            colSelect.setComponentName("chkopt");
            addColumn(colSelect);


    }

}
