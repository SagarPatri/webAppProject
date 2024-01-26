/**
 * @ (#)  IntimationsTable.java July 19,2006
 * Project      : TTK HealthCare Services
 * File         : IntimationsTable.java
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : July 19,2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class IntimationsTable extends Table {
	/**
	 * This creates the columnproperties objects for each and
	 * every column and adds the column object to the table
	 */

		public void setTableProperties()
		{
			setRowCount(10);
			setCurrentPage(1);
			setPageLinkCount(10);

			//Setting properties for Intimation Date
			Column colDesc = new Column("Intimation Date");
			colDesc.setMethodName("getClaimIntimationDate");
			colDesc.setColumnWidth("12%");
			colDesc.setIsLink(true);
			colDesc.setLinkTitle("Edit Intimation Date");
			colDesc.setIsHeaderLink(true);
			colDesc.setHeaderLinkTitle("Sort by Intimation Date");
			colDesc.setDBColumnName("INTIMATION_DATE");
			addColumn(colDesc);

			//Setting properties for Estimated Amt. (Rs)
			Column colAccountHead = new Column("Estimated Amt. (Rs)");
			colAccountHead.setMethodName("getEstimatedAmt");
			colAccountHead.setColumnWidth("14%");
			colAccountHead.setIsHeaderLink(true);
			colAccountHead.setHeaderLinkTitle("Sort by Estimated Amt. (Rs)");
			colAccountHead.setDBColumnName("ESTIMATED_AMOUNT");
			addColumn(colAccountHead);

			//Setting properties for Ailment Details
			Column colReqAmt = new Column("Ailment Details");
			colReqAmt.setMethodName("getAilmentDesc");
			colReqAmt.setColumnWidth("12%");
			colReqAmt.setIsHeaderLink(true);
			colReqAmt.setHeaderLinkTitle("Sort by Ailment Details");
			colReqAmt.setDBColumnName("AILMENT_DESCRIPTION");
			addColumn(colReqAmt);

			//Setting properties for Hospital Name
			Column colAllowedAmt = new Column("Hospital Name");
			colAllowedAmt.setMethodName("getHospitalName");
			colAllowedAmt.setColumnWidth("16%");
			colAllowedAmt.setIsHeaderLink(true);
			colAllowedAmt.setHeaderLinkTitle("Sort by Hospital Name ");
			colAllowedAmt.setDBColumnName("HOSP_NAME");
			addColumn(colAllowedAmt);

			//Setting properties for Hospital Name
            Column colLikelyDate = new Column("Likely Date of Hosp.");
            colLikelyDate.setMethodName("getClaimLikelyDateOfHosp");
            colLikelyDate.setColumnWidth("17%");
            colLikelyDate.setIsHeaderLink(true);
            colLikelyDate.setHeaderLinkTitle("Sort by Likely Date of Hosp.");
            colLikelyDate.setDBColumnName("LIKELY_DATE_OF_HOSPITALISATION");
            addColumn(colLikelyDate);
            
            //KOC for 1339 MAIL READER
            
            Column colPatientName = new Column("Patient Name.");
            colPatientName.setMethodName("getPatientName");
            colPatientName.setColumnWidth("17%");
            colPatientName.setIsHeaderLink(true);
            colPatientName.setHeaderLinkTitle("Sort by Patient Name.");
            colPatientName.setDBColumnName("patient_name");
            addColumn(colPatientName);
            
            Column colSource = new Column("Source.");
            colSource.setMethodName("getSource");
            colSource.setColumnWidth("15%");
            colSource.setIsHeaderLink(true);
            colSource.setHeaderLinkTitle("Sort by Source.");
            colSource.setDBColumnName("source_from");
            addColumn(colSource);
            
            //KOC for 1339 MAIL READER
            
		}//end of public void setTableProperties()
}//end of class IntimationsTable