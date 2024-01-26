/**
 * @ (#) ClmReqAmtTable.java
 * Project       : TTK HealthCare Services
 * File          : ClmReqAmtTable.java
 * Author        : Balakrishna Erram
 * Company       : Span Systems Corporation
 * Date Created  : 10th August,2010
 *
 * @author       : 
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.maintenance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ClmReqAmtTable extends Table{

	 public void setTableProperties()
	    {
		    setRowCount(10);
	        setCurrentPage(1);
	        setPageLinkCount(10);
	        
	        //Setting properties for Claim No.
	        Column colClaimNo = new Column("Claim No.");
	        colClaimNo.setMethodName("getClaimNbr");
	        colClaimNo.setColumnWidth("20%");
	        colClaimNo.setIsLink(true);
	        colClaimNo.setLinkTitle("Edit Claim No.");
	        colClaimNo.setDBColumnName("CLAIM_NUMBER");
	        addColumn(colClaimNo);
	        
	        //Setting properties for Scheme No.
	        Column colPolicyNo = new Column("Scheme No.");
	        colPolicyNo.setMethodName("getPolicyNbr");
	        colPolicyNo.setColumnWidth("20%");
	        colPolicyNo.setDBColumnName("POLICY_NUMBER");
	        addColumn(colPolicyNo);
	        
	        //Setting properties for Enrollment Id.
	        Column colEnrollID = new Column("Enrollment Id");
	        colEnrollID.setMethodName("getEnrollmentID");
	        colEnrollID.setColumnWidth("20%");
	        colEnrollID.setDBColumnName("ENROLLMENT_ID");
	        addColumn(colEnrollID);
	        
	        //Setting properties for Member Name
	        Column colClaimantName = new Column("Member Name");
	        colClaimantName.setMethodName("getClaimantName");
	        colClaimantName.setColumnWidth("27%");
	        colClaimantName.setDBColumnName("CLAIMANT_NAME");
	        addColumn(colClaimantName);
	        
	        //Setting properties for Requested Amt.
	        Column colPolicySubType = new Column("Requested Amt. (Rs)");
	        colPolicySubType.setMethodName("getPreClmReqAmt");
	        colPolicySubType.setColumnWidth("13%");
	        colPolicySubType.setDBColumnName("REQUESTED_AMT");
	        addColumn(colPolicySubType);     
	            
	    }//end of public void setTableProperties()

}//end of class ClmReqAmtTable
