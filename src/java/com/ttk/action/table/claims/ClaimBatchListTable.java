/**
 * @ (#) ClaimBatchListTable.java July 7, 2015
 * Project 	     : ProjectX
 * File          : ClaimBatchListTable.java
 * Author        : Nagababu K
 * Company       : RCS Technologies
 * Date Created  : July 7, 2015
 *
 * @author       :  Nagababu K
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class ClaimBatchListTable extends Table
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5197596089165070753L;

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
	public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Batch No
        Column colBatchNo=new Column("Batch No");
        colBatchNo.setMethodName("getBatchNO");
        colBatchNo.setColumnWidth("20%");
        colBatchNo.setIsHeaderLink(true);
        colBatchNo.setIsLink(true);
        colBatchNo.setImageName("");
        colBatchNo.setImageTitle("");
        colBatchNo.setHeaderLinkTitle("Sort by: Batch No");
        colBatchNo.setDBColumnName("BATCH_NO");
        addColumn(colBatchNo);
        //Setting properties for Provider Name.
        Column colProviderName=new Column("Provider Name");
        colProviderName.setMethodName("getProviderName");
        colProviderName.setColumnWidth("20%");
        colProviderName.setIsHeaderLink(true);
        colProviderName.setHeaderLinkTitle("Sort by: Provider Name");
        colProviderName.setDBColumnName("HOSP_NAME");
        addColumn(colProviderName);
        
        Column colClaimType=new Column("Claim Type");
        colClaimType.setMethodName("getClaimType");
        colClaimType.setColumnWidth("20%");
        colClaimType.setIsHeaderLink(true);
        colClaimType.setHeaderLinkTitle("Sort by: Claim Type");
        colClaimType.setDBColumnName("CLAIM_TYPE");
        addColumn(colClaimType);
        
        
        Column colSubmissionType=new Column("Submission Category");
        colSubmissionType.setMethodName("getProcessType");
        colSubmissionType.setColumnWidth("20%");
        colSubmissionType.setIsHeaderLink(true);
        colSubmissionType.setHeaderLinkTitle("Sort by: Submission Category");
        colSubmissionType.setDBColumnName("submission_catogory");
        addColumn(colSubmissionType);
        

        //Setting properties for  Type of Batch Creation Date
        Column colBatchcreationDate=new Column("Batch Received Date");
        colBatchcreationDate.setMethodName("getBatchReceiveDate");
        colBatchcreationDate.setColumnWidth("20%");
        colBatchcreationDate.setIsHeaderLink(true);
        colBatchcreationDate.setHeaderLinkTitle("Sort by: Batch Received Date");
        colBatchcreationDate.setDBColumnName("RECEIVED_DATE");
        addColumn(colBatchcreationDate);
        
        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setColumnWidth("1%");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);
        
    } //end of setTableProperties()
}// end of ClaimBatchListTable