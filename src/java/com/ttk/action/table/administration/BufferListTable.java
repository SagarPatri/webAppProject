/**
* @ (#) BufferListTable.java Jun 19, 2006
* Project 		: TTK HealthCare Services
* File			: BufferListTable.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created  : Jun 19, 2006
*
* @author 		: Pradeep R
* Modified by 	:
* Modified date :
* Reason :
*/
package com.ttk.action.table.administration;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class BufferListTable extends Table {


    public void setTableProperties() {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Ref. No.
        Column colRefNo = new Column("Ref. No.");
        colRefNo.setMethodName("getRefNbr");
        colRefNo.setColumnWidth("20%");
        colRefNo.setIsLink(true);
        colRefNo.setLinkTitle("Edit Ref. No.");
        colRefNo.setIsHeaderLink(true);
        colRefNo.setHeaderLinkTitle("Sort by Ref. No.");
        colRefNo.setDBColumnName("REFERENCE_NO");
        addColumn(colRefNo);

        //Setting properties for Buffer Date
        Column colBuffDate = new Column("Buffer Date");
        colBuffDate.setMethodName("getBufferAddedDate");
        colBuffDate.setColumnWidth("10%");
        colBuffDate.setIsHeaderLink(true);
        colBuffDate.setHeaderLinkTitle("Sort by Buffer Date");
        colBuffDate.setDBColumnName("BUFFER_ADDED_DATE");
        addColumn(colBuffDate);

        //Setting properties for Buffer Amt. (AED)
        Column colBuffAmt = new Column("Normal Corpus Fund (OMR)");
        colBuffAmt.setMethodName("getBufferAmt");
        colBuffAmt.setColumnWidth("10%");
        colBuffAmt.setIsHeaderLink(true);
        colBuffAmt.setHeaderLinkTitle("Sort by Normal Corpus Fund (AED)");
        colBuffAmt.setDBColumnName("BUFFER_AMOUNT");
        addColumn(colBuffAmt);
        
        //<!-- added for hyundai buffer by rekha on 19.06.2014 -->
        Column colNormMedAmt = new Column("Normal Medical Fund (OMR)");
        colNormMedAmt.setMethodName("getNormMedAmt");
        colNormMedAmt.setColumnWidth("10%");
        colNormMedAmt.setIsHeaderLink(true);
        colNormMedAmt.setHeaderLinkTitle("Sort by Normal Medical Fund (AED)");
        colNormMedAmt.setDBColumnName("MED_BUFFER_AMOUNT");
        addColumn(colNormMedAmt);
      
        //Setting properties for Buffer Amt. (AED)
        Column colCritiCorpAmt = new Column("Critical Corpus Fund (OMR)");
        colCritiCorpAmt.setMethodName("getCritiCorpAmt");
        colCritiCorpAmt.setColumnWidth("10%");
        colCritiCorpAmt.setIsHeaderLink(true);
        colCritiCorpAmt.setHeaderLinkTitle("Sort by Critical Corpus Fund (AED)");
        colCritiCorpAmt.setDBColumnName("CRITICAL_CORP_BUFF_AMOUNT");
        addColumn(colCritiCorpAmt);
        
      //Setting properties for Buffer Amt. (AED)
        
        Column colCritiMedAmt = new Column("Critical Medical Fund (OMR)");
        colCritiMedAmt.setMethodName("getCritiMedAmt");
        colCritiMedAmt.setColumnWidth("10%");
        colCritiMedAmt.setIsHeaderLink(true);
        colCritiMedAmt.setHeaderLinkTitle("Sort by Critical Medical Fund (AED)");
        colCritiMedAmt.setDBColumnName("CRITICAL_MED_BUFF_AMOUNT");
        addColumn(colCritiMedAmt);
        
        
        //Setting properties for Buffer Amt. (AED)
          
          Column colCritiIllnessBuffAmt = new Column("Critical Illness Buffer Fund (OMR)");
          colCritiIllnessBuffAmt.setMethodName("getCriIllBufferAmt");
          colCritiIllnessBuffAmt.setColumnWidth("10%");
          colCritiIllnessBuffAmt.setIsHeaderLink(true);
          colCritiIllnessBuffAmt.setHeaderLinkTitle("Sort by Illness Buffer Fund (AED)");
          colCritiIllnessBuffAmt.setDBColumnName("CRITICAL_BUFF_AMOUNT");
          addColumn(colCritiIllnessBuffAmt);
        
      //<!-- end added for hyundai buffer by rekha on 19.06.2014 -->
        //Setting properties for Mode
        Column colMode = new Column("Mode");
        colMode.setMethodName("getModeDesc");
        colMode.setColumnWidth("20%");
        colMode.setIsHeaderLink(true);
        colMode.setHeaderLinkTitle("Sort by Mode");
        colMode.setDBColumnName("BUFFER_MODE");
        addColumn(colMode);

    }

}

