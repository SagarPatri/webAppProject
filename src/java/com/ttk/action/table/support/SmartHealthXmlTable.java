/**
 * @ (#) SmartHealthXmlTable.java 15 NOV 2016
 * Project      : Project X
 * File         : SmartHealthXmlTable.java
 * Author       : Nagababu K
 * Company      : RCS
 * Date Created : 15 NOV 2016
 *
 * @author       : Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.support;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 *  This class provides the information of Investigation details
 *
 */
public class SmartHealthXmlTable extends Table{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(40);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for File ID.
        Column fileID = new Column("File ID");
        fileID.setMethodName("getFileID");
        fileID.setColumnWidth("25%");
        fileID.setIsHeaderLink(true);
        fileID.setHeaderLinkTitle("Sort by: File ID");
        fileID.setIsLink(true);
        fileID.setLinkTitle("View File");
        fileID.setDBColumnName("FILE_ID");
        addColumn(fileID);
        //Setting properties for File ID.
        Column fileName = new Column("File NAME");
        fileName.setMethodName("getFileName");
        fileName.setColumnWidth("30%");
        fileName.setIsHeaderLink(true);
        fileName.setHeaderLinkTitle("Sort by: File Name");
        fileName.setIsLink(true);
        fileName.setLinkTitle("Download File");
        fileName.setDBColumnName("FILE_NAME");
        fileName.setLinkParamName("SecondLink");      
        addColumn(fileName);
        
        
        //Provider ID
        Column provider_ID = new Column("Provider ID");
        provider_ID.setMethodName("getProviderID");
        provider_ID.setColumnWidth("10%");
        provider_ID.setIsHeaderLink(true);
        provider_ID.setHeaderLinkTitle("Sort by: Provider ID");    
        provider_ID.setDBColumnName("Provider_ID");
        addColumn(provider_ID);
        
        
           //Setting properties for Recieved Frome.
          Column recievedFrom = new Column("Recieved Date");
          recievedFrom.setMethodName("getXmlRecievedData");
          recievedFrom.setColumnWidth("10%");
          recievedFrom.setIsHeaderLink(true);
          recievedFrom.setHeaderLinkTitle("Sort by: Recieved From");    
          recievedFrom.setDBColumnName("DOWN_LOAD_DATE");
          addColumn(recievedFrom);
          
          //Setting properties for Download Status.
          Column downloadData = new Column("Download Date");
          downloadData.setMethodName("getUserDownloadDate");
          downloadData.setColumnWidth("10%");
          downloadData.setIsHeaderLink(true);
          downloadData.setHeaderLinkTitle("Sort by: Download Date");    
          downloadData.setDBColumnName("USER_DOWNLOADED_DATE");
          addColumn(downloadData);
          
        //Setting properties for Download Status.
          Column downloadStatus = new Column("Download Status");
          downloadStatus.setMethodName("getUserDownloadStatus");
          downloadStatus.setColumnWidth("8%");
          downloadStatus.setIsHeaderLink(true);
          downloadStatus.setHeaderLinkTitle("Sort by: Download Status");    
          downloadStatus.setDBColumnName("USER_DOWNLOADED_YN");
          addColumn(downloadStatus);
          
          Column dhpoTxDate = new Column("DHPO Tx Date");
          dhpoTxDate.setMethodName("getDhpoTxDate");
          dhpoTxDate.setColumnWidth("10%");
          dhpoTxDate.setIsHeaderLink(true);
          dhpoTxDate.setHeaderLinkTitle("Sort by: DHPO Transaction Date");    
          dhpoTxDate.setDBColumnName("DHPO_TX_DATE");
          addColumn(dhpoTxDate);
          
          Column dhpoClaimCnt = new Column("DHPO Claim Cnt");
          dhpoClaimCnt.setMethodName("getDhpoClaimRecCount");
          dhpoClaimCnt.setColumnWidth("3%");
          dhpoClaimCnt.setIsHeaderLink(true);
          dhpoClaimCnt.setHeaderLinkTitle("Sort by: DHPO Claim Count");    
          dhpoClaimCnt.setDBColumnName("DHPO_TOTAL_REC_CNT");
          addColumn(dhpoClaimCnt);
       
          Column shClaimCnt = new Column("SH Claim Cnt");
          shClaimCnt.setMethodName("getClaimRecCount");
          shClaimCnt.setColumnWidth("2%");
          shClaimCnt.setIsHeaderLink(true);
          shClaimCnt.setHeaderLinkTitle("Sort by: SmartHealth Claim Count");    
          shClaimCnt.setDBColumnName("CLM_REC_CNT");
          addColumn(shClaimCnt);
          //Setting properties for check box
        Column colSelect = new Column("Select");
      colSelect.setColumnWidth("2%");
      colSelect.setComponentType("checkbox");
      colSelect.setComponentName("chkopt");
     addColumn(colSelect);
     
    }//end of setTableProperties()

}//end of InvestigationTable
