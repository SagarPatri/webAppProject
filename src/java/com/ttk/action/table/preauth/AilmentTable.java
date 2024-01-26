
/**
 * @ (#) AilmentTable Sept 13th 2007
 * Project      : TTK HealthCare Services
 * File         : InvestigationTable.java
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : Sept 13th 2007
 *
 * @author       : Balakrishna Erram
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class AilmentTable extends Table {

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
            colAilment.setColumnWidth("23%");
            colAilment.setIsLink(true);
            colAilment.setDBColumnName("PED_DESCRIPTION");
            addColumn(colAilment);

            //Setting properties for ICD Code
            Column colICDCode = new Column("ICD Code");
            colICDCode.setMethodName("getICDCode");
            colICDCode.setColumnWidth("9%");
            colICDCode.setDBColumnName("ICD_CODE");
            addColumn(colICDCode);

            //Setting properties for Procedures
            Column colProcedures = new Column("Procedures");
            colProcedures.setMethodName("getProcCodes");
            colProcedures.setColumnWidth("22%");
            colProcedures.setDBColumnName("PROC_CODE");
            addColumn(colProcedures);
			
			Column colDiagnosisName = new Column("Diagnosis Name");
            colDiagnosisName.setMethodName("getDiagnosisType");
            colDiagnosisName.setColumnWidth("15%");
            colDiagnosisName.setDBColumnName("DIAGNOSYS_TYPE");
            addColumn(colDiagnosisName);  
            
            Column colDiagnosisType = new Column("Diagnosis Type");
            colDiagnosisType.setMethodName("getDiagnType");
            colDiagnosisType.setColumnWidth("15%");
            colDiagnosisType.setDBColumnName("DIAG_TYPE");
            addColumn(colDiagnosisType);
            
//          Setting properties for image Delete
            Column colImage = new Column("Delete");
            colImage.setIsImage(true);
            colImage.setIsImageLink(true);
            colImage.setImageName("getDeleteName");
            colImage.setImageTitle("getDeleteTitle");
            addColumn(colImage);
            
    }//end of public void setTableProperties()

}//end of AilmentTable
