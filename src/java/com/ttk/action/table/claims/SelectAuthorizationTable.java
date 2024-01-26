/**
 * @ (#)  SelectAuthorizationTable.java July 17,2006
 * Project      : TTK HealthCare Services
 * File         : SelectAuthorizationTable.java
 * Author       : Harsha Vardhan B N
 * Company      : Span Systems Corporation
 * Date Created : July 17,2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;


public class SelectAuthorizationTable extends Table {
	/**
	 * This creates the columnproperties objects for each and
	 * every column and adds the column object to the table
	 */

		public void setTableProperties()
		{
			setRowCount(10);
			setCurrentPage(1);
			setPageLinkCount(10);

			//Setting properties for Authorization No
			Column colAuthNo = new Column("Authorization No.");
			colAuthNo.setMethodName("getAuthNum");
			colAuthNo.setColumnWidth("18%");
			colAuthNo.setIsLink(true);
			colAuthNo.setLinkTitle("Edit Authorization No");
			colAuthNo.setIsHeaderLink(true);
			colAuthNo.setHeaderLinkTitle("Sort by Authorization No.");
			colAuthNo.setDBColumnName("AUTH_NUMBER");
			addColumn(colAuthNo);

			//Setting properties for Hospital Name
			Column colHospName = new Column("Hospital Name");
			colHospName.setMethodName("getHospitalName");
			colHospName.setColumnWidth("31%");
			colHospName.setIsHeaderLink(true);
			colHospName.setHeaderLinkTitle("Sort by Hospital Name");
			colHospName.setDBColumnName("HOSP_NAME");
			addColumn(colHospName);

			//Setting properties for Member Name
			Column colClaimantName = new Column("Member Name");
			colClaimantName.setMethodName("getClaimantName");
			colClaimantName.setColumnWidth("31%");
			colClaimantName.setIsHeaderLink(true);
			colClaimantName.setHeaderLinkTitle("Sort by Member Name");
			colClaimantName.setDBColumnName("MEM_NAME");
			addColumn(colClaimantName);

			//Setting properties for Received Date
			Column colRcvdDate = new Column("Date of Admission");
			colRcvdDate.setMethodName("getClmAdmissionTime");
			colRcvdDate.setColumnWidth("20%");
			colRcvdDate.setIsHeaderLink(true);
			colRcvdDate.setHeaderLinkTitle("Sort by Received Date");
			colRcvdDate.setDBColumnName("HOSPITALIZATION_DATE");
			addColumn(colRcvdDate);


			//Setting properties for check box
			/*Column colSelect = new Column("Select");
			colSelect.setComponentType("checkbox");
			colSelect.setComponentName("chkopt");
			addColumn(colSelect);*/
		}//end of public void setTableProperties()
}//end of class SelectAuthorizationTable
